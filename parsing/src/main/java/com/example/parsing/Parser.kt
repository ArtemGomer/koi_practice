
import android.util.Log
import com.example.parsing.FirestoreWriter
import data_model.Category
import data_model.Product
import data_model.Subcategory
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException

class Parser {
    companion object {
        private const val baseURL = "https://www.auchan.ru"
        const val logTag = "!@#"
    }

    private fun getFullUrl(link: String): String {
        return baseURL + link
    }

    @Throws(IOException::class)
    fun parseProduct(link: String, name: String): Product {
        val document: Document = Jsoup.connect(getFullUrl(link)).get()
        val tableRow: Element = document.select("th:contains(Ингредиенты)").first().parent()
        val ingredients = tableRow.getElementsByTag("td").text()
        return Product(name = name, ingredients = ingredients)
    }

    private fun getNextPage(document: Document): Element? {
        val elements: Elements = document.getElementsByClass("pagination-arrow--right")
        return elements.first()
    }

    @Throws(IOException::class)
    fun parseSubCategory(link: String, name: String): Subcategory {
        val productList = mutableListOf<Product>()
        Log.i(logTag,"Parsing subcategory: $name")
        var document: Document = Jsoup.connect(getFullUrl(link)).get()
        var countProductsInSub = 0
        while (true) {
            val linkToProducts: Elements = document.getElementsByClass("linkToPDP")
            countProductsInSub += linkToProducts.size
            for (i in 1..1) {
                val product: Element = linkToProducts[i]
                try {
                    val newProduct: Product = parseProduct(product.attr("href"), product.text())
                    productList.add(newProduct)
                    Log.i(logTag, "Parsed product: " + newProduct.name)
                } catch (ignored: IOException) {}
            }
            val nextPage: Element? = getNextPage(document)
            document = if (nextPage != null) {
                val newLink: String = nextPage.getElementsByTag("a").first().attr("href")
                Log.i(logTag, "Go to page $newLink in subcategory $name")
                Jsoup.connect(getFullUrl(newLink)).get()
            } else {
                break
            }
        }
        Log.i(logTag, "There are $countProductsInSub products in subcategory $name")
        return Subcategory(subCategoryName = name, productList = productList)
    }

    @Throws(IOException::class)
    fun parseCategory(link: String, name: String) {
        Log.i(logTag, "Parse category \"$name\" by URL: " + getFullUrl(link))
        val document: Document = Jsoup.connect(getFullUrl(link)).get()
        val linkToSubCategories: Elements = document.getElementsByClass("linkToSubCategory")
        val subcategoryList = mutableListOf<Subcategory>()
        for (element in linkToSubCategories) {
            try {
                subcategoryList.add(parseSubCategory(element.attr("href"), element.text()))
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        val writer = FirestoreWriter()
        writer.write(Category(categoryName = name, subcategoryList = subcategoryList))
    }

}