#!/usr/bin/env groovy

def doJavaBindings(buildType=Release) {
  def currentPath = sh(script: "pwd", returnStdout: true).trim()
  def artifactsPath = "$currentPath/java-bindings.zip"
  sh """
    cmake \
      -H. \
      -Bbuild \
      -DCMAKE_BUILD_TYPE=$buildType \
      -DSWIG_JAVA=ON
  """
  sh "cd build; make -j${params.PARALLELISM} irohajava"
  sh "zip -j $artifactsPath build/shared_model/bindings/*.java build/shared_model/bindings/libirohajava.so"
  return artifactsPath
}

def doPythonBindings(buildType=Release) {
  def currentPath = sh(script: "pwd", returnStdout: true).trim()
  def artifactsPath = "$currentPath/python-bindings.zip"
  sh """
    cmake \
      -H. \
      -Bbuild \
      -DCMAKE_BUILD_TYPE=$buildType \
      -DSWIG_PYTHON=ON
  """
  sh "cmake --build build --target python_tests"
  sh "cd build; make -j${params.PARALLELISM} irohapy"
  sh "zip -j $artifactsPath build/shared_model/bindings/*.py build/shared_model/bindings/_iroha.so"
  return artifactsPath
}

def doAndroidBindings(abiVersion) {
  def currentPath = sh(script: "pwd", returnStdout: true).trim()
  def artifactsPath = "$currentPath/android-bindings-$PLATFORM-$abiVersion-$BUILD_TYPE.zip"
  sh """
    (cd /iroha; git init; git remote add origin https://github.com/hyperledger/iroha.git; \
    git fetch --depth 1 origin develop; git checkout -t origin/develop)
  """
  // SWIG fixes magic
  sh 'sed -i.bak "s~find_package(JNI REQUIRED)~#find_package(JNI REQUIRED)~" /iroha/shared_model/bindings/CMakeLists.txt'
  sh 'sed -i.bak "s~# the include path to jni.h~SET(CMAKE_SWIG_FLAGS \${CMAKE_SWIG_FLAGS} -package ${PACKAGE})~" /iroha/shared_model/bindings/CMakeLists.txt'
  sh 'sed -i.bak "s~swig_link_libraries(irohajava~swig_link_libraries(irohajava \"/protobuf/.build/lib${PROTOBUF_LIB_NAME}.a\" \"${NDK_PATH}/platforms/android-$abiVersion/${ARCH}/usr/${LIBP}/liblog.so\"~" /iroha/shared_model/bindings/CMakeLists.txt'
  sh 'sed -i.bak "s~find_library(protobuf_LIBRARY protobuf)~find_library(protobuf_LIBRARY ${PROTOBUF_LIB_NAME})~" /iroha/cmake/Modules/Findprotobuf.cmake'
  sh 'sed -i.bak "s~find_program(protoc_EXECUTABLE protoc~set(protoc_EXECUTABLE \"/protobuf/host_build/protoc\"~" /iroha/cmake/Modules/Findprotobuf.cmake'
  sh """
    cmake -H/iroha/shared_model -B/iroha/shared_model/build -DCMAKE_SYSTEM_NAME=Android -DCMAKE_SYSTEM_VERSION=$abiVersion -DCMAKE_ANDROID_ARCH_ABI=$PLATFORM \
    -DANDROID_NDK=$NDK_PATH -DCMAKE_ANDROID_STL_TYPE=c++_static -DCMAKE_BUILD_TYPE=$BUILD_TYPE -DTESTING=OFF \
    -DSHARED_MODEL_DISABLE_COMPATIBILITY=ON -DSWIG_JAVA=ON -DCMAKE_PREFIX_PATH=$DEPS_DIR
  """
  sh "cmake --build /iroha/shared_model/build --target irohajava -- -j${params.PARALLELISM}"
  sh "zip $artifactsPath /iroha/shared_model/build/bindings/*.java /iroha/shared_model/build/bindings/libirohajava.so"
  return artifactsPath
}

return this
