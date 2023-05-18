package ru.ilya.filmslist.presentation.ui.detailedScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ru.ilya.filmslist.R
import ru.ilya.filmslist.databinding.DetailedFragmentBinding
import ru.ilya.filmslist.domain.models.DetailedFilmItem

class DetailedFragment : Fragment() {

    private var _binding: DetailedFragmentBinding? = null
    private val binding: DetailedFragmentBinding
        get() = _binding ?: throw RuntimeException("DetailedFragmentBinding == null")

    private var filmItemId: Long = UNKNOWN
    private lateinit var viewModel: DetailedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this)[DetailedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailedFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        initObservers()
        getDetailFilm()
    }

    private fun getDetailFilm() {
        viewModel.getDetailFilmFromApi(id = filmItemId)
    }

    private fun initClickListeners() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initObservers() {
        viewModel.stateDetailFilm.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailedFilmState.Loading -> {
                    binding.progressLoader.visibility = View.VISIBLE
                }
                is DetailedFilmState.Success -> {
                    binding.progressLoader.visibility = View.GONE
                    setupDetailInfo(state.detailedFilmItem)
                }
                is DetailedFilmState.Error -> {
                    binding.progressLoader.visibility = View.GONE
                    setupErrorPicture()
                    Toast.makeText(requireActivity(), state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupDetailInfo(detailedFilmItem: DetailedFilmItem) {
        Glide.with(requireActivity()).load(detailedFilmItem.posterURL).into(binding.moviePoster)
        val genre = detailedFilmItem.genres.map { it.genre.replaceFirstChar(Char::titlecase) }
        val country =
            detailedFilmItem.countries.map { it.country.replaceFirstChar(Char::titlecase) }

        binding.movieTitle.text = detailedFilmItem.name
        binding.description.text = detailedFilmItem.description
        binding.countries.text = country.joinToString { it }
        binding.genres.text = genre.joinToString { it }
    }

    private fun setupErrorPicture() {
        binding.moviePoster.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.no_connection,
                null
            )
        )
        val layoutParams = binding.moviePoster.layoutParams
        layoutParams.width = PICTURE_SIZE
        layoutParams.height = PICTURE_SIZE
        binding.moviePoster.layoutParams = layoutParams
    }

    private fun parseParams() {
        val args = requireArguments() // переданные аргументы, приложение крашнется, если их нет
        if (!args.containsKey(FILM_ITEM_ID)) {
            throw RuntimeException("Param FILM_ITEM_ID mode is absent!")
        }
        val parsedId = args.getLong(FILM_ITEM_ID, -1L)
        filmItemId = parsedId
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val FILM_ITEM_ID = "film_item_id"
        private const val UNKNOWN = -1L
        private const val PICTURE_SIZE = 400

        fun newInstanceDetailedFilmItem(filmId: Long): DetailedFragment {
            return DetailedFragment().apply {
                arguments = Bundle().apply {
                    putLong(FILM_ITEM_ID, filmId)
                }
            }
        }
    }
}