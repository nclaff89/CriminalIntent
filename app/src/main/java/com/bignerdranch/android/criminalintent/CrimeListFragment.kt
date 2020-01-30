package com.bignerdranch.android.criminalintent
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        callbacks = context as Callbacks?
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
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    /**
     * chapter 12 challenge
     */
    private fun updateUI(crimes: List<Crime>){
        adapter?.submitList(crimes)
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
            solvedImageView.visibility = if(crime.isSolved) {
                View.VISIBLE
            }else{
                View.GONE
            }
        }

        override fun onClick(v: View?) {
            callbacks?.onCrimneSelected(crime.id)
        }
    }

    /**
     * Chapter 12 challenge make Crime adappter extend
     * ListAdapter instead of Adapter
     */
    private inner class CrimeAdapter(var crimes: List<Crime>)
        :androidx.recyclerview.widget.ListAdapter<Crime, CrimeHolder>(CrimeDiffCallback()){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }



        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val item  = getItem(position)
            holder.bind(item)

        }

    }

    /**
     * Chapter 12 challenge 1
     */
    private inner class CrimeDiffCallback: DiffUtil.ItemCallback<Crime>(){
        override fun areItemsTheSame(oldItem: Crime, newItem: Crime): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Crime, newItem: Crime): Boolean {
           return oldItem == newItem
        }
    }

    companion object{
        fun newInstance(): CrimeListFragment{
            return CrimeListFragment()
        }
    }


}