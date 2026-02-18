error id: file:///C:/Users/User/Documents/GitHub/Scala-Project/movie/src/main/scala/Main.scala:scala/collection/immutable/List#foreach().
file:///C:/Users/User/Documents/GitHub/Scala-Project/movie/src/main/scala/Main.scala
empty definition using pc, found symbol in pc: scala/collection/immutable/List#foreach().
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1973
uri: file:///C:/Users/User/Documents/GitHub/Scala-Project/movie/src/main/scala/Main.scala
text:
```scala
import com.opencsv.CSVReader
import java.io.FileReader
import scala.util.Using
import scala.io.StdIn.readLine
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
  try
    Using(CSVReader(new FileReader(path))) { reader =>
      val rows = reader.readAll().asScala.toList

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
    }.toEither.left.map(_.getMessage)
  catch
    case e: Exception =>
      Left(e.getMessage)

def topMovies(movies: List[Movie],n: Int): List[String] =
  movies
    .sortBy(- _.vote_average)
    .take(n)
    .zipWithIndex
    .map { (movie, index) =>
      s"${index + 1}. ${movie.title} - ${movie.vote_average}"
    }

def topMoviesWithYears(movies: List[Movie],n: Int,year: Int): List[String] =
  movies
    .filter(_.release_date.startsWith(year.toString))
    .sortBy(- _.vote_average)
    .take(n)
    .zipWithIndex
    .map { (movie, index) =>
      s"${index + 1}. ${movie.title} - ${movie.vote_average}"
    }

@main def run(): Unit =
  loadMovies("D:/download/top_rated_movies.csv") match
    case Right(movies) =>
      println(s"Loaded ${movies.length} movies")
      println(
        """
Choose an option:
1. Top n Rated Movies of all time
2. Top n Rated Movies by Year
3. Most Popular Movie
4. Movies in the year
""".stripMargin
      )
      val choice = readLine("Enter choice: ")
      choice match
        case "1" =>
          val n = readLine("Enter number: ").toInt
          val result = topMovies(movies,n)
          result.@@foreach(println)
        case "2" => 
          val 
    case Left(error) =>
      println("Error: " + error)


```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/collection/immutable/List#foreach().