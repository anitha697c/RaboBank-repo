<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>

<h1>Rabo Bank Statement Processor</h1>
<I> Please upload a file for validation (Supported file types csv/xml) </I><br>
<br>
<form method="POST" action="/rabobank/processStatement" enctype="multipart/form-data">
    <input type="file" name="file" /><br/><br/>
    <input type="submit" value="Submit" />
</form>

</body>
</html>