from pprint import pprint

from boto3.dynamodb.conditions import Key

from config import client, dynamodb
from schema import table_name_attribute_map


table_names = client.list_tables().get("TableNames", [])

for table_name, attribute_map in table_name_attribute_map.items():
    table = dynamodb.Table(table_name)

    if table_name in table_names:
        print(f"\nTable {table_name}\n")
        res = table.scan(AttributesToGet=list(attribute_map.values()))
        pprint(res)

        for key in res["Items"]:
            item = table.get_item(TableName=table_name, Key=key)
            pprint(item)

    else:
        print(f'Table "{table_name}" does not exist\n')
