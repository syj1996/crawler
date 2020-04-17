### 拥有一个微信个人订阅号，附上登陆和注册链接。微信公众平台
![1](https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTcxMTE4MTkxMzExMzM2)
**出了一个小窗口，选择“查找文章”，输入需要查找的公众号，这里用“科技美学”公众号作为例子**
![2](https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTcxMTE4MTkxNTM4MTk5)
![3](https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTcxMTE4MTkxNzA1NzQy)

```(Java)

# 目标url
url = "https://mp.weixin.qq.com/cgi-bin/appmsg"

# 使用Cookie，跳过登陆操作
headers = {
  "Cookie": yourcookie,
  "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36",
}

"""
需要提交的data
以下个别字段是否一定需要还未验证。
注意修改yourtoken,number
number表示从第number页开始爬取，为5的倍数，从0开始。如0、5、10……
token可以使用Chrome自带的工具进行获取
fakeid是公众号独一无二的一个id，等同于后面的__biz
"""
data = {
    "token": yourtoken,
    "lang": "zh_CN",
    "f": "json",
    "ajax": "1",
    "action": "list_ex",
    "begin": number,
    "count": "5",
    "query": "",
    "fakeid": yourfakeid,
    "type": "9",
}

# 使用get方法进行提交
content_json = requests.get(url, headers=headers, params=data).json()
# 返回了一个json，里面是每一页的数据
for item in content_json["app_msg_list"]:
    # 提取每页文章的标题及对应的url
    print(item["title"], "url": item["link"])

```

