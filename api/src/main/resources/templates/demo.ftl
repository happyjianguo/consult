<#if jsonBean??>
  <html>
  <head>
    <title>demoPage</title>
  </head>
  <body>
  输出bean：
  id:${jsonBean.id}<br/>
  name:${jsonBean.name}<br/>
  date:${jsonBean.createTime!"没有时间"}<br/>
  <br/><br/>
  if语句：
  <#if jsonBean.id == 990>
    990 is id
  <#else>
    id is not 990
  </#if>
  for循环：
  <div>
      <#list jsonBean.list as bean>
          ${bean},
      </#list>
  </div>

  定义方法：
  <div>
      <#function sum x y>
          <#return (x + y)>
      </#function>
    id与20的和：${sum(jsonBean.id, 20)}
  </div>
  </body>
  </html>
<#else>
  没有数据
</#if>