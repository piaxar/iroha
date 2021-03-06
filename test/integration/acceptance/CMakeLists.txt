#
# Copyright Soramitsu Co., Ltd. 2018 All Rights Reserved.
# http://soramitsu.co.jp
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#        http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

addtest(tx_acceptance_test tx_acceptance_test.cpp)
target_link_libraries(tx_acceptance_test
        application
        raw_block_insertion
        model_generators
        integration_framework
        shared_model_stateless_validation
        )
addtest(get_transactions_test get_transactions_test.cpp)
target_link_libraries(get_transactions_test
  application
  integration_framework
  shared_model_proto_builders
  shared_model_stateless_validation
  )

addtest(add_asset_qty_test add_asset_qty_test.cpp)
target_link_libraries(add_asset_qty_test
  application
  integration_framework
  shared_model_proto_builders
  shared_model_stateless_validation
  )

addtest(create_role_test create_role_test.cpp)
target_link_libraries(create_role_test
  application
  integration_framework
  shared_model_proto_builders
  shared_model_stateless_validation
  )

addtest(invalid_fields_test invalid_fields_test.cpp)
target_link_libraries(invalid_fields_test
  application
  integration_framework
  shared_model_proto_builders
  shared_model_stateless_validation
  )

addtest(transfer_asset_test transfer_asset_test.cpp)
target_link_libraries(transfer_asset_test
  application
  integration_framework
  shared_model_proto_builders
  shared_model_stateless_validation
  )

addtest(create_domain_test create_domain_test.cpp)
target_link_libraries(create_domain_test
  application
  integration_framework
  shared_model_proto_builders
  shared_model_stateless_validation
  )

addtest(subtract_asset_qty_test subtract_asset_qty_test.cpp)
target_link_libraries(subtract_asset_qty_test
  application
  integration_framework
  shared_model_proto_builders
  shared_model_stateless_validation
  )

addtest(create_account_test create_account_test.cpp)
target_link_libraries(create_account_test
  application
  integration_framework
  shared_model_proto_builders
  shared_model_stateless_validation
  )
