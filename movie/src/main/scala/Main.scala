import com.opencsv.CSVReader
import java.io.FileReader
import scala.util.Using
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

def top10Formatted(movies: List[Movie]): List[String] =
  movies
    .sortBy(- _.vote_average)
    .take(10)
    .zipWithIndex
    .map { (movie, index) =>
      s"${index + 1}. ${movie.title} - ${movie.vote_average}"
    }


@main def run(): Unit =
  loadMovies("D:/download/top_rated_movies.csv") match
    case Right(movies) =>
      val result = top10Formatted(movies)
      result.foreach(println)
    case Left(error) =>
      println("Error: " + error)

