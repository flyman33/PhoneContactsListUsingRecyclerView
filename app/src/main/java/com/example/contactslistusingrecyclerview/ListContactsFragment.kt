package com.example.contactslistusingrecyclerview

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ListContactsFragment: Fragment(){

    private lateinit var contactListRecyclerView: RecyclerView
    private var adapter = PhoneBookAdapter()
    lateinit var list: MutableList<Person>
    lateinit var displayList: MutableList<Person>

    companion object {
        fun newInstance() = ListContactsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_contacts_fragment, container, false)
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            contactListRecyclerView = view.findViewById(R.id.contact_list)
            contactListRecyclerView.adapter = adapter

            if (MainActivity.isOld) {
                parentFragmentManager.setFragmentResultListener(
                    "KEY",
                    viewLifecycleOwner
                ) { key, bund ->
                    if (key == "KEY") {

                        val name = bund.getString(ContactFragment.NAME_EXTRA)
                        val lastname = bund.getString(ContactFragment.LASTNAME_EXTRA)
                        val phoneNumber = bund.getString(ContactFragment.PHONE_NUMBER_EXTRA)
                        val position = bund.getInt(ContactFragment.ID_EXTRA)
                        adapter.people.set(
                            position,
                            Person(name.toString(), lastname.toString(), phoneNumber!!.toLong(),
                                "https://source.unsplash.com/random/300x200?sig=${position}")
                        )
                        adapter.notifyItemChanged(position)
                    }
                }
            } else {
                list = mutableListOf<Person>()
                displayList = mutableListOf<Person>()

                val nameList = mutableListOf("Никон","Лев","Ипполит","Арсен","Рубен","Агап","Бронислав",
                    "Моисей","Онуфрий","Эммануил","Леонид","Дементий","Антип","Андрон","Всеслав",
                    "Яромир","Богдан","Марк","Эрик","Савва","Денис","Олег","Фадей","Клим","Архип",
                    "Егор","Артем","Август","Изяслав","Максимильян","Федот","Адриан","Юлий","Гавриил",
                    "Эмиль","Степан","Зиновий","Данис","Камиль","Емельян","Серафим","Авдей","Евсей",
                    "Соломон","Семён","Евграф","Поликарп","Евгений","Евангелина","Элина","Марьяна",
                    "Лейла","Мариам","Мирослава","Оксана","Галина","Майя","Марианна","Эмилия","Виталина",
                    "Инесса","Агата","Кристина","Эвелина","Стефания","Айлин","Амира","Дария","Лада",
                    "Аделя","Ясмин","Василина","Карина","Алла","Алёна","Яна","Мария","Лия","Марфа",
                    "Аврора","Амалия","Арина","Розалия","Анфиса","Кира","Милослава","Мия","Евгения",
                    "Анисия","Христина","Тея","Мадина","Оливия","Аида","Нина","Пелагея","Наталья",
                    "Мила","Камилла","Альфия","Алеся","Алсу","Вероника","Таисия","Альбина","Рената",
                    "Изольда","Зоя","Лиана","Медина"
                )

                val lastnameList = mutableListOf("Анохин","Ямщиков","Краснобаев","Благово","Яфаров",
                    "Яковенко","Кулаков","Яницкий","Якимович","Телицын","Лассман","Пугин",
                    "Сухомлин","Александрин","Владимиров","Ерофеев","Шабалин","Трибой","Зуйков",
                    "Стегнов","Горбачев","Гончаров","Деревсков","Комолов","Ясырев","Яфраков",
                    "Разин","Сарана","Соловьёв","Федосеев","Букирь","Щавельский","Борщёв",
                    "Кобонов","Кравец","Яснеев","Янко","Лепихов","Глоба","Андрианов","Яхненко",
                    "Апакидзе","Бунин","Хватов","Вятт","Кондрат","Волков","Ломоносов","Кахадзе",
                    "Проскуркина","Витаева","Кизюрина","Серова","Молодцова","Никерхоева","Куксилина",
                    "Мишнева","Голубкина","Берия","Саянова","Татаринцева","Большакова","Жжёнова",
                    "Титова","Арзамасцева","Рязанцева","Лепихова","Юганцева","Бажанова","Звягина",
                    "Федченкова","Килиса","Морозова","Селезнева","Курбанова","Головкина","Астахова",
                    "Трибой","Шапиро","Володина","Кручинкина","Кашникова","Щепкина","Жирова","Никифорова",
                    "Кулактина","Сорокина","Лукина","Шмелева","Ермишина","Кондратова","Лещенко","Сутулина",
                    "Агеева","Бармыкина","Ожогина","Ягупова","Ягунова","Меркушева","Сенотрусова","Фонвизина",
                    "Карчагина","Страхова","Веденина","Угличинина","Панарина","Апалкова","Щитта","Паршина","Силиванова"
                )

                val phoneNumberList = mutableListOf(89017071898,89017669019,89019185542,89011498586,
                    89018357234,89010091288,89019675106,89014600464,89019001451,89015168940,89011742815,
                    89017542186,89018632147,89016355865,89013032185,89013915876,89014996474,89010069629,
                    89012324183,89018506373,89016341092,89019815016,89014876711,89016110858,89019279191,
                    89018369432,89011958164,89018687162,89019000922,89015860199,89019420078,89013654285,
                    89018538756,89014336229,89017217906,89016279021,89018476939,89016308500,89012202265,
                    89018389608,89014614909,89014950953,89017359018,89014681676,89013763869,89015425057,
                    89019379459,89012809561,89013821414,89016646223,89015110609,89019984694,89018622862,
                    89013705947,89015852941,89014031071,89014302936,89010789265,89015004523,89012702520,
                    89015125598,89016758306,89010735975,89015641850,89016391277,89015289248,89012281042,
                    89010668272,89010103982,89010033526,89014484810,89018670499,89010089804,89011726083,
                    89013281490,89015820725,89012380420,89010210054,89018352264,89014782930,89017821136,
                    89015816469,89019552201,89017997309,89019210582,89016371074,89012925932,89013246347,
                    89016942763,89010050790,89019358785,89010084557,89014142776,89017285285,89012090851,
                    89016931403,89012729487,89011573424,89013472081,89018573612,89017322932,89014516389,
                    89011463506,89013457770,89011370705,89015678560,89010796866,89010298903,89013101162,
                    89013068764
                )

                for (i in 0 until nameList.size) {
                    list.add(Person(nameList[i], lastnameList[i], phoneNumberList[i],
                        "https://source.unsplash.com/random/300x200?sig=${i}"))
                }

                displayList.addAll(list)
                adapter.people = displayList
            }
            setHasOptionsMenu(true)

            contactListRecyclerView.apply {
                setHasFixedSize(true)
                val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
                addItemDecoration(itemDecoration)
            }
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

        val item: MenuItem = menu.findItem(R.id.search)
        val searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               if(newText!!.isNotEmpty()) {
                    displayList.clear()
                    val search = newText.lowercase(Locale.getDefault())

                    for(person in list) {
                       if(person.name.lowercase(Locale.getDefault()).contains(search) ||
                           person.lastname.lowercase(Locale.getDefault()).contains(search) ) {
                           displayList.add(person)
                       }
                       contactListRecyclerView.adapter!!.notifyDataSetChanged()
                   }
               } else {
                   displayList.clear()
                   displayList.addAll(list)
                   contactListRecyclerView.adapter!!.notifyDataSetChanged()
               }
                return true
            }
        })
    }
}