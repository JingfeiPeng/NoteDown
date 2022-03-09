package business.markdown

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class JsoupEmitter : HtmlTreeEmitter {
    override fun emitFrom(from: String): Element {
        return Jsoup.parse(from).body()
    }
}