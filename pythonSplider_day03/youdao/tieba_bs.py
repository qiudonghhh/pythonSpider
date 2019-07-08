import requests
from bs4 import BeautifulSoup
'''
爬取百度热点排名，关键词，热度（用bs4爬取，爬取的是html）
'''


def get_html(url, headers):
    #请求并返回数据
    results=requests.get(url,headers=headers)
    results.encoding=results.apparent_encoding
    return results.text


def get_page(result):
    #用BeautifulSoup将数据解析成html形式
    results=BeautifulSoup(result,"html.parser")
    #查找html的tr标签（返回的是一个列表，从第二个开始（经分析第一个tr的不是关键字））
    contents=results.find_all("tr")[1:]
    #遍历这个列表，拿到每个的tr标签的内容
    for content in contents:
        #查找取出每个标签的td里的关键字，排名，热度
        # （因为html的关键字，排名，热度的td标签分别是keyword，first，last）
        content_kw=content.find("td",class_="keyword")
        content_first=content.find("td",class_="first")
        content_last=content.find("td",class_="last")
        if content_kw !=None and content_first != None and content_last!=None:
            #去除掉空字符，换行符，和search
            kw=content_kw.get_text().replace(' ','').replace('\n','').replace('search','')
            first=content_first.get_text().replace(' ','').replace('\n','').replace('search','')
            last=content_last.get_text().replace(' ','').replace('\n','').replace('search','')
            print("排名:"+first+",     关键词:"+kw+",     热度"+last)



if __name__ == '__main__':
    #百度热点——实时热点url
    url="http://top.baidu.com/buzz?b=1&fr=topcategory_c513"
    headers = {
        "user-agent": "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36"
    }
    result=get_html(url,headers)
    get_page(result)



