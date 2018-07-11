# fileuploader

A simple Spring Boot HTTP API that stores multipart file uploads to a configurable location on disk.

## configuration

`fileuploader` uses environment variables for configuration:

* FILEUP_MAX_SIZE - maximum size of file uploads
* FILEUP_MAX_REQUEST - maximum request size
* FILEUP_PORT - the listen port
* FILEUP_ADDR - the server bind address
* FILEUP_DIR - the location on disk to store files (must exist)

## package

Maven can be used to create a fat-jar:

```
mvn package
..
..
i[INFO] --- maven-jar-plugin:3.0.2:jar (default-jar) @ fileuploader ---
[INFO] Building jar: /home/user/IdeaProjects/fileuploader/target/fileuploader-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.0.3.RELEASE:repackage (default) @ fileuploader ---
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 7.861 s
[INFO] Finished at: 2018-07-11T14:58:40+10:00
[INFO] Final Memory: 27M/293M
[INFO] ------------------------------------------------------------------------
```
## deployment

Copy the jar file into a suitable location (eg; /opt/fileuploader/ )

`fileuploader` can then be deployed with a systemd unit file;

```
[Unit]
Description=File Uploader
After=network.target

[Service]
Type=simple
Environment=FILEUP_MAX_SIZE=200MB
Environment=FILEUP_MAX_REQUEST=210MB
Environment=FILEUP_PORT=8200
Environment=FILEUP_ADDR=0.0.0.0
Environment=FILEUP_DIR=/tmp/uploads
ExecStart=/bin/java -jar /opt/fileuploader/fileuploader.jar

[Install]
WantedBy=multi-user.target
```

## posting data

JQuery's '.ajax' function can be used to simply store data to the API. Create a file `index.html` with the following content:

```
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>AJAX File Upload With Spring Boot</title>
<script
 src="https://code.jquery.com/jquery-3.3.1.min.js"
 integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
 crossorigin="anonymous"></script>
  <script type="text/javascript">
    $(document).ready(function() {
       $("#file-form").submit(function(e) {
         $.ajax({
           type: 'POST',
           url: 'http://localhost:8200/api/upload',
           processData: false,
           data: new FormData($("#file-form")[0]),
           enctype: 'multipart/form-data',
           contentType: false,
           cache: false,
           success: function(data) {
             alert("success! "+data);
          },
           error: function(data) {
             alert("error! "+data);
          }
      });
      e.preventDefault(); // avoid the default submit of the form
      });
    });
</script>
</head>
<body>
<form id="file-form" action="" method="post" enctype="multipart/form-data">
    Upload a File:
    <input type="file" id="uploadfile" name="uploadfile">
    <input type="submit" id="submit" name="submit" value="Upload File Now" >
</form>
<p id="status"></p>
</body>
</html>
```
Start a basic python HTTP server:
```
python -m SimpleHTTPServer 8080
```

Browse to http://localhost:8080, select a file, and select the 'Upload File Now' button. You should now see the file saved into the configured directory.
