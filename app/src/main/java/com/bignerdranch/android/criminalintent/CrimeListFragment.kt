package com.bignerdranch.android.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_crime_list.*
import kotlinx.android.synthetic.main.list_item_crime.*
import java.util.*

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    /**
     * Required for hosting activities
     */
    interface Callbacks{
        fun onCrimneSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null


    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    /**
     * Chapter 14 challenge
     */
    private lateinit var emptyTv: TextView
    private lateinit var emptyButton: Button

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerView =
            view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let{
                    Log.i(TAG, "Got crimes: ${crimes.size}")

                    updateUI(crimes)
                }
            }
                )

        /**
         * Chapter 14 challenge
         * IMPORTANT!! be sure to see the changes made to
         * the fragment_crime_list XML layout
         */
        emptyButton = view.findViewById(R.id.new_crime_bu)
        emptyTv = view.findViewById(R.id.no_crime_tv)

        emptyButton.setOnClickListener {
            val crime = Crime()
            crimeListViewModel.addCrime(crime)
            callbacks?.onCrimneSelected(crime.id)

        }



    }


    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.new_crime ->{
                val crime = Crime()
                crimeListViewModel.addCrime(crime)
                callbacks?.onCrimneSelected(crime.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)

        }
    }

    private fun updateUI(crimes: List<Crime>){
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter

        /**
         * Chapter 14 challenge
         * IMPORTANT!! Be sure to see the changes made
         * to the fragment_crime_list XML file!
         */
        if(crimes.isEmpty() || adapter == null){
            emptyButton.visibility = VISIBLE
            emptyTv.visibility = VISIBLE
        }else {
            emptyButton.visibility = GONE
            emptyTv.visibility = GONE

        }
    }

    private  inner class CrimeHolder(view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var crime: Crime

        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
       private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
       private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime){
                this.crime = crime
                titleTextView.text = this.crime.title
                dateTextView.text = this.crime.date.toString()
                solvedImageView.visibility = if (crime.isSolved) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

        }


        override fun onClick(v: View?) {
            callbacks?.onCrimneSelected(crime.id)
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>)
        :RecyclerView.Adapter<CrimeHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)


            return CrimeHolder(view)
        }

        override fun getItemCount() = crimes.size


        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)


        }


    }

    companion object{
        fun newInstance(): CrimeListFragment{
            return CrimeListFragment()
        }
    }

}