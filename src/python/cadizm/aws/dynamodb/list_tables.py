from pprint import pprint

from config import client


pprint(client.list_tables().get("TableNames", []))
