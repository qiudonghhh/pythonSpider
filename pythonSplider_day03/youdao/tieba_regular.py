import urllib.parse
import urllib.request
import re
'''
分页爬取百度贴吧标题（用正则表达式爬取，爬取的是html）
'''

def loadSplider(url, startPage, endPage):
    for page in range(startPage,endPage+1):
        print("当前第"+str(page)+"页")
        pn=(page-1)*50
        fullurl=url+"&"+"pn="+str(pn)
        html=data(fullurl).decode("UTF-8")
        #匹配正则表达式
        parrent=re.compile('<div\sclass="threadlist_title pull_left j_th_tit ">(.*?)</div>',re.S)
        #查找匹配的数据，返回列表
        contene_list=parrent.findall(html)
        dealPage(contene_list)



def dealPage(contene_list):
    #遍历列表
    for item in contene_list:
        #截取列表里的<a>标签
        item=item.replace("<a>","")
        # 匹配正则表达式（返回的是列表）
        title=re.findall(r'<a.*?title="(.*?)".*?</a>',item)
        href =re.findall(r'<a.*?href="(.*?)".*?</a>',item)
        #parrent = re.compile('title="(.*?)"', re.S)
        url="http://tieba.baidu.com"+href[0]
        print(title[0])
        #print(href[0])
        # html=secondSpider(url)
        # # 匹配正则表达式
        # parrent = re.compile('<div\sid="post_content_120963787431".*?>(.*?)</div>', re.S)
        # # 查找匹配的数据，返回列表
        # second_list = parrent.findall(html)
        # dealPage(contene_list)






def secondSpider(url):
    headers = {
        "user-agent": "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36"
    }
    request = urllib.request.Request(url, headers=headers)
    respone = urllib.request.urlopen(request)
    return respone.read()



def data(url):
    headers={
        "user-agent":"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36"
    }
    request=urllib.request.Request(url,headers=headers)
    respone=urllib.request.urlopen(request)
    return respone.read()

if __name__ == '__main__':
    kw=input("请输入您要爬取的贴吧")
    startPage=int(input("请输入开始页"))
    endPage=int(input("请输入终止页"))
    url="http://tieba.baidu.com/f?"
    key=urllib.parse.urlencode({"kw":kw})
    fullurl=url+key
    loadSplider(fullurl,startPage,endPage)



