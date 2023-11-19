from config import ENV


TABLE_NAME = f"dynamodb.{ENV}.dynamic.config"

table_name_schema_map = {
    TABLE_NAME: dict(
        TableName=TABLE_NAME,
        KeySchema=[
            {"AttributeName": "key", "KeyType": "HASH"},
            # {
            #     'AttributeName': 'skey',
            #     'KeyType': 'RANGE'
            # }
        ],
        AttributeDefinitions=[
            {"AttributeName": "key", "AttributeType": "S"},
            # {
            #     'AttributeName': 'skey',
            #     'AttributeType': 'S'
            # }
        ],
        BillingMode="PAY_PER_REQUEST",
        # BillingMode='PROVISIONED',
        # ProvisionedThroughput={
        #     'ReadCapacityUnits': 100,
        #     'WriteCapacityUnits': 100
        # }
    )
}


table_name_attribute_map = {
    TABLE_NAME: dict(
        partition_key_name="key",
    ),
    # TABLE_NAME_2: dict(
    #     partition_key_name='key',
    #     sort_key_name='skey',
    #     ttl_key='ttl'
    # )
}
