package com.rafalbiarda.medcinedontforgetversion8.ui.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import com.rafalbiarda.medcinedontforgetversion8.R
import com.rafalbiarda.medcinedontforgetversion8.firebase.FirestoreClass
import com.rafalbiarda.medcinedontforgetversion8.model.Card
import com.rafalbiarda.medcinedontforgetversion8.ui.fragments.LoginFragmentDirections
import com.rafalbiarda.medcinedontforgetversion8.ui.fragments.PinLockFragment
import com.rafalbiarda.medcinedontforgetversion8.ui.fragments.PinLockFragmentDirections
import com.rafalbiarda.medcinedontforgetversion8.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.calendar_item.view.*
import java.text.SimpleDateFormat
import java.util.*

//class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
class MainActivity : BaseActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var bottomNav: BottomNavigationView
    lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout
    lateinit var myCalendarViewManager : CalendarViewManager
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("TestIng", "it.toString()" )

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        

        bottomNav = findViewById(R.id.bottomNavigationView)
        drawerLayout = findViewById(R.id.drawer_layout)
        navController = findNavController(R.id.nav_host_fragment_container)
        navigationView.setupWithNavController(navController)

        setSupportActionBar(toolbar)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)

        //navigationView.setNavigationItemSelectedListener(this)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.medicineFragment,
                R.id.healthFragment,
                R.id.statisticsFragment
            ), drawerLayout
        )

        createNotificationChannel()

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNav.setupWithNavController(navController)



        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

                R.id.loginFragment, R.id.registerFragment, R.id.forgotPasswordFragment -> {
                    bottomNav.visibility = View.GONE
                    main_single_row_calendar.visibility = View.GONE
                    appBarLayout.visibility = View.GONE
                }
                R.id.medicineFragment, R.id.healthFragment -> {
                    bottomNav.visibility = View.VISIBLE
                    main_single_row_calendar.visibility = View.VISIBLE
                    appBarLayout.visibility = View.VISIBLE
                }
                R.id.statisticsFragment
                -> {
                    bottomNav.visibility = View.VISIBLE
                    main_single_row_calendar.visibility = View.GONE

                }
                else -> {
                    bottomNav.visibility = View.GONE
                    main_single_row_calendar.visibility = View.GONE
                }
            }
        }

        setupCalendar()

//        if(FirestoreClass().getCurrentUserID().isNotEmpty())
//        {
//            viewModel.setUserCards()
//            viewModel.setUserMedicineList()
//            viewModel.setUserDoctorList()
//            fromPinToMedicineFragment()
//        }

    }

    override fun onStart() {
        super.onStart()

        viewModel.setUserCards()
        viewModel.setUserMedicineList()
        viewModel.setUserDoctorList()

        val action = PinLockFragmentDirections.actionGlobalPinLockFragment()
        navController.navigate(action)



    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    internal fun fromPinToMedicineFragment()
    {
        val action = LoginFragmentDirections.actionLoginFragmentToMedicineFragment()
        navController.navigate(action)
    }
    internal fun setToolbar(str : String)
    {
        tvToolbarTitle.text = str
    }



    fun setupCalendar() {
        // set current date to calendar and current month to currentMonth variable
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        var currentMonth = calendar[Calendar.MONTH]

        // paste into onCreate method
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        }

        myCalendarViewManager = object : CalendarViewManager {
            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {
                // bind data to calendar item views
                holder.itemView.tv_date_calendar_item.text = DateUtils.getDayNumber(date)
                holder.itemView.tv_day_calendar_item.text = DateUtils.getDay3LettersName(date)
            }

            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {
                val calendar = Calendar.getInstance(Locale.ENGLISH)
                calendar.time = date

                // return item layout files, which you have created
                return if (isSelected) {
                    R.layout.selected_calendar_item
                } else {
                    R.layout.calendar_item
                }
            }
        }

        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                // return true if item can be selected
                return true
            }
        }

        val myCalendarChangesObserver = object : CalendarChangesObserver {
            override fun whenWeekMonthYearChanged(
                weekNumber: String,
                monthNumber: String,
                monthName: String,
                year: String,
                date: Date
            ) {
                super.whenWeekMonthYearChanged(weekNumber, monthNumber, monthName, year, date)
            }

            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                super.whenSelectionChanged(isSelected, position, date)
                //tvDate.text = "${DateUtils.getMonthName(date)}, ${DateUtils.getDayNumber(date)} "
                val datee = "${DateUtils.getMonthName(date)}, ${DateUtils.getDayNumber(date)} "
                tvToolbarTitle.text = datee
                //val day = DateUtils.getDayName(date)
                //tvDay.text = DateUtils.getDayName(date)

                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                viewModel.setDateSetByUser(formatter.parse(formatter.format(date)))

                var isAnyData = false
                viewModel.userCards.value?.forEach { card ->
                    if(isSameDay(card.date!!, date))
                    {
                        viewModel.setActualCard(card)
                        viewModel.setHealthCards(card.health)
                        isAnyData = true
                    }
                }
                if(!isAnyData)
                {
                    viewModel.setActualCard(Card())
                }

            }

            fun isSameDay(date1: Date, date2: Date): Boolean
            {
                val cal1 = Calendar.getInstance()
                val cal2 = Calendar.getInstance()
                cal1.time = date1
                cal2.time = date2

                return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
            }

            override fun whenCalendarScrolled(dx: Int, dy: Int) {
                super.whenCalendarScrolled(dx, dy)
            }

            override fun whenSelectionRestored() {
                super.whenSelectionRestored()
            }

        }

        val singleRowCalendar = main_single_row_calendar.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            futureDaysCount = 30
            includeCurrentDate = true
            init()
        }
    }

    fun createNotificationChannel()
    {
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
        {
            val name = "MyChannel"
            val description = "Channel for me"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("myID", name, importance)
            channel.description = description

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }





}