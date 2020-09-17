$var = $(get-item ${PWD}).parent.FullName
$pathProject = $var + "\jmetal-webPage"
$pathJar = $pathProject + "\target"

#Create jar jmetal-webPage
docker run -it --rm --name jmetal-webPage -v ${pathProject}:/usr/src/mymaven -w /usr/src/mymaven maven mvn package -DskipTests

#Move jar to actual directory
mv ${pathJar}/jmetal-webPage-0.1.0.jar .

#Create image
docker build -t harenderdhanoya/jmetal-webpage:1.0 .