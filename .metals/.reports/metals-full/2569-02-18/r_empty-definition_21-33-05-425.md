error id: file:///C:/Users/User/Documents/GitHub/Scala-Project/movie/src/main/scala/Main.scala:scala/Predef.println(+1).
file:///C:/Users/User/Documents/GitHub/Scala-Project/movie/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -scala/jdk/CollectionConverters.println.
	 -scala/jdk/CollectionConverters.println#
	 -scala/jdk/CollectionConverters.println().
	 -println.
	 -println#
	 -println().
	 -scala/Predef.println.
	 -scala/Predef.println#
	 -scala/Predef.println().
offset: 1003
uri: file:///C:/Users/User/Documents/GitHub/Scala-Project/movie/src/main/scala/Main.scala
text:
```scala
import com.opencsv.CSVReader
import java.io.FileReader
import scala.jdk.CollectionConverters.*

case class Movie(
  id: Int,
  title: String,
  overview: String,
  release_date: String,
  popularity: Double,
  vote_average: Double,
  vote_count: Int
)

def loadMovies(path: String): Either[String, List[Movie]] =
  var reader: CSVReader | Null = null

  try
    reader = CSVReader(new FileReader(path))
    val rows = reader.readAll().asScala.toList

    val movies =
      rows.tail.map { row =>
        Movie(
          row(0).toInt,
          row(1),
          row(2),
          row(3),
          row(4).toDouble,
          row(5).toDouble,
          row(6).toInt
        )
      }

    Right(movies)

  catch
    case e: Exception =>
      Left(e.getMessage)

  finally
    if reader != null then reader.close()

def printTop10(movies: List[Movie]): Unit =
  val top10 =
    movies
      .sortBy(- _.vote_average)   // descending
      .take(10)

  p@@rintln("Top 10 Movies by Rating:")
  top10.zipWithIndex.foreach { (movie, index) =>
    println(s"${index + 1}. ${movie.title} - ${movie.vote_average}")
  }

@main def run(): Unit =
  loadMovies("D:/download/top_rated_movies.csv") match
    case Right(movies) =>
      printTop10(movies)
    case Left(error) =>
      println("Error: " + error)


```


#### Short summary: 

empty definition using pc, found symbol in pc: 