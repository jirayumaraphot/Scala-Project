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

@main def run(): Unit =
  // สามารถเปลี่ยนที่ตั้งของ file ได้
  val reader = CSVReader(new FileReader("C:/Users/User/Documents/GitHub/Scala-Project/top_rated_movies.csv"))

  val rows = reader.readAll().asScala.toList
  reader.close()

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

  println(s"Loaded ${movies.length} movies")

  println("\nTop 10 Highest Rated:")
  movies
    .sortBy(- _.vote_average)
    .take(10)
    .foreach(m => println(s"${m.title} - ${m.vote_average}"))
