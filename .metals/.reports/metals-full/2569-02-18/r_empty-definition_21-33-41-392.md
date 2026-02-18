error id: file:///C:/Users/User/Documents/GitHub/Scala-Project/movie/src/main/scala/Main.scala:scala/collection/SeqOps#sortBy().
file:///C:/Users/User/Documents/GitHub/Scala-Project/movie/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: scala/collection/SeqOps#sortBy().
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -scala/jdk/CollectionConverters.movies.sortBy.
	 -scala/jdk/CollectionConverters.movies.sortBy#
	 -scala/jdk/CollectionConverters.movies.sortBy().
	 -movies/sortBy.
	 -movies/sortBy#
	 -movies/sortBy().
	 -scala/Predef.movies.sortBy.
	 -scala/Predef.movies.sortBy#
	 -scala/Predef.movies.sortBy().
offset: 932
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

def top10Formatted(movies: List[Movie]): List[String] =
  movies
    .@@sortBy(- _.vote_average)
    .take(10)
    .zipWithIndex
    .map { (movie, index) =>
      s"${index + 1}. ${movie.title} - ${movie.vote_average}"
    }


@main def run(): Unit =
  loadMovies("D:/download/top_rated_movies.csv") match
    case Right(movies) =>
      printTop10(movies)
    case Left(error) =>
      println("Error: " + error)


```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/collection/SeqOps#sortBy().