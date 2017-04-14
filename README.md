# hdm-wim-fallstudie-cep-spark

*Hinweis: Bei diesem Projekt handelt es sich um ein Forschungsprojekt der Hochschule der Medien, Stuttgart.*

## Entwicklungsumgebung einrichten

*Hinweis: Die folgende Anleitung gilt für eine Windows Umgebung

1. Scala 2.11.8 als **.zip** herunterladen. ([www.scala-lang.org](https://www.scala-lang.org/download/2.11.8.html)) und nach
 `"C:/dev/scala/"` entpacken.
2. `"SCALA_HOME"` mit dem Verweis `"C:/dev/scala/scala-2.11.8"` als Umgebungsvariable hinzufügen.
3. `"%SCALA_HOME%\bin"` zum Path hinzufügen.
4. Um 2. & 3. zu testen in der Eingabeaufforderung `scala` eingeben. Dort sollte dann `scala>` stehen.

### Apache Spark installieren

1. Aktuellen Build von Apache Spark herunterladen ([www.spark.apache.org](http://spark.apache.org/downloads.html)) in unserem Fall **spark-2.1.0-bin-hadoop2.7** und nach `C:/dev/spark/` entpacken.
2. `"SPARK_HOME"` mit dem Verweis auf `"C:/dev/spark/spark-2.1.0-bin-hadoop2.7"` als Umgebungsvariable hinzufügen.
3. `"%SPARK_HOME%\bin"` zum Path hinzufügen.
4. `winutils.exe` für die entsprechende Version von hadoop [herunterladen](https://github.com/steveloughran/winutils) (2.7) und nach `C:/dev/hadoop/bin` kopieren.
5. `"HADOOP_HOME"` mit dem Verweis auf `"C:/dev/hadoop/"` als Umgebungsvariable hinzufügen.

Im Folgenden wird beschrieben, wie man Apache Spark im *standalone* modus, also einen lokalen Cluster lokal ausführt.

Der Spark Application Master ist dafür verantwortlich resource requests auf die entsprechenden worker threads umzuleiten.
Um den Master zu starten folgenden Befehl in die Eingabeaufforderung eingeben:

`spark-class.cmd org.apache.spark.deploy.master.Master`

Jetzt im Browser folgende Adresse eingeben: `http://localhost:8080/`. Dort steht die IP des Spark Masters `spark://ip:port`. 
Mit dem folgenden Befehl in einer weiteren Eingabeaufforderung, wird ein worker angelegt und beim Master angelegt. 

`spark-class.cmd org.apache.spark.deploy.worker.Worker spark://ip:port`

Nun sollte der Worker auch auf der Master Seite im Browser zu sehen sein.

spark shell:

`spark-shell –master ip.des.spark.masters`

um eine jar application auszuführen:

`spark-submit –class mainclassname –master spark://ip:port pfadzurjar`