# PoiDemo
springboot项目上传下载excel，并通过poi把对应数据导入导出到数据库中

使用`Workbook wb = WorkbookFactory.create(inp);`
用这种方法创建Workbook可以使解析上传的excel同时兼容xls、xlsx两种格式

处理火狐和Safari浏览器 中文文件名乱码：

```
response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
```
