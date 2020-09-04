If(Test-Path .\jmetal-webPage* -PathType Leaf){
    rm jmetal-webPage-0.1.0.jar
}
docker-compose down
.\create_docker_image.ps1
docker-compose up