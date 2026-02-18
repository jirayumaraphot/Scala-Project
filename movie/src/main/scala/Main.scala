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
          safeToInt(row(0)),
          row(1),
          row(2),
          row(3),
          safeToDouble(row(4)),
          safeToDouble(row(5)),
          safeToInt(row(6))
        )
      }
    }.toEither.left.map(_.getMessage)
  catch
    case e: Exception =>
      Left(e.getMessage)

def topMovies(movies: List[Movie],n: Int): List[String] =
  movies
    .sortBy(m => -m.vote_average)
    .take(n)
    .zipWithIndex
    .map { (movie, index) =>
      s"${index + 1}. ${movie.title} - ${movie.vote_average}"
    }

def topMoviesWithYears(movies: List[Movie],n: Int,year: Int): List[String] =
  movies
    .filter(_.release_date.startsWith(year.toString))
    .sortBy(m => -m.vote_average)
    .take(n)
    .zipWithIndex
    .map { (movie, index) =>
      s"${index + 1}. ${movie.title} - ${movie.vote_average}"
    }
def mostPopular(movies: List[Movie]): Option[Movie] =
  movies.reduceOption((a, b) =>
    if a.popularity >= b.popularity then a else b
  )

def moviesReleased(movies: List[Movie], year: Int): List[String] =
  movies
    .filter(_.release_date.startsWith(year.toString))
    .sortBy(_.release_date)
    .zipWithIndex
    .map { (movie, index) =>
      s"${index + 1}. ${movie.title} - ${movie.release_date}"
    }


def mostVotedMovie(movies: List[Movie],n: Int): List[String] =
  movies
    .sortBy(m => -m.vote_count)
    .take(n)
    .zipWithIndex
    .map { (movie, index) =>
      s"${index + 1}. ${movie.title} - ${movie.vote_count}"
    }

def mostVotedMovieByYear(movies: List[Movie],n: Int,year: Int): List[String] =
  movies
    .filter(_.release_date.startsWith(year.toString))
    .sortBy(m => -m.vote_count)
    .take(n)
    .zipWithIndex
    .map { (movie, index) =>
      s"${index + 1}. ${movie.title} - ${movie.vote_count}"
    }

def numberMoviesReleased(movies: List[Movie],year: Int): Int =
  movies
    .filter(_.release_date.startsWith(year.toString))
    .length

@main def run(): Unit =
  loadMovies("D:/download/top_rated_movies.csv") match
    case Right(movies) =>
      println(s"Loaded ${movies.length} movies")
      println(
        """
        |Choose an option:
        |1. Top n Rated Movies of all time
        |2. Top n Rated Movies by Year
        |3. Most Popular Movie
        |4. Movies released by that year
        |5. Top n most voted Movies of all time
        |6. Top n most voted Movies by year
        |7. Number of Movies released by that year
        |""".stripMargin
      )
      val choice = readLine("Enter choice: ")
      choice match
        case "1" =>
          val n = readLine("Enter number: ").toInt
          val result = topMovies(movies,n)
          result.foreach(println)
        case "2" => 
          val n = readLine("Enter number: ").toInt
          val year = readLine("Enter year: ").toInt
          val result = topMoviesWithYears(movies,n,year)
          if result.isEmpty then
            println("No movies found for that year.")
          else
            result.foreach(println)
        case "3" =>
          mostPopular(movies) match
            case Some(result) =>
              println(s"Most Popular: ${result.title} - Popularity: ${result.popularity}")
            case None =>
              println("No movies found.")
        case "4" =>
          val year = readLine("Enter year: ").toInt
          val result = moviesReleased(movies,year)
          if result.isEmpty then
            println("No movies found for that year.")
          else
            result.foreach(println)
        case "5" =>
          val n = readLine("Enter number: ").toInt
          val result = mostVotedMovie(movies,n)
          result.foreach(println)
        case "6" =>
          val n = readLine("Enter number: ").toInt
          val year = readLine("Enter year: ").toInt
          val result = mostVotedMovieByYear(movies,n,year)
          if result.isEmpty then
            println("No movies found for that year.")
          else
            result.foreach(println)
        case "7" =>
          val year = readLine("Enter year: ").toInt
          val result = numberMoviesReleased(movies,year)
    case Left(error) =>
      println("Error: " + error)
