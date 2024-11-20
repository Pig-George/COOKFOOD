import json
from pymongo import MongoClient


# JSON文件路径
file_path = 'recipe_corpus_full.json'
# MongoDB 连接设置
client = MongoClient("mongodb://192.168.121.130:27017")  # 修改为你的MongoDB连接字符串
db = client["cookFood"]  # 替换为你的数据库名称
collection = db["recipe"]  # 替换为你的集合名称

# 逐项解析大JSON文件并存储到MongoDB
def parse_and_store_json(file_path):
    num = 0
    cutn = 0
    with open(file_path, 'r', encoding='utf-8') as file:
        for line in file:
            cutn += 1
            if cutn > 440277 :
                try:
                    item = json.loads(line.strip())  # 逐行解析 JSON 数据
                    collection.insert_one(item)  # 插入每条记录到MongoDB
                    num += 1
                    print("现插入：%d个, 已插入：%d" % (num, cutn))  # 可选：打印插入的记录
                except json.JSONDecodeError as e:
                    print("JSON解析错误:", e, "出错行:", line)

# 调用函数，解析并存储数据
parse_and_store_json(file_path)