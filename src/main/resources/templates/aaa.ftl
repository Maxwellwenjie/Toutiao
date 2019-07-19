<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<pre>
    Hello FreeMaker!
</pre>
this is index page
</br>
${value1}

<#list color as item>
    ${item}<br/>
</#list>

<br/>
<#list map?keys as key>
    ${key}:${map[key]}<br/>
</#list>

${user.name} </br>
${user.getName()}</br>

Set : <#assign title="nowcoder">
Include : <#include "head">
import : <#import "head" as head></br>

<#macro render_color color1>
    <#list color as item>
         color1=${item} </br>
         index1=${item_index}</br>
    </#list>
</#macro>
<@render_color color1=color />

</body>
</html>