package com.dianakarenms.dogslisttd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dianakarenms.data.models.Dog
import com.dianakarenms.utils.PictureTools
import kotlinx.android.synthetic.main.activity_dog_details.*
import kotlinx.android.synthetic.main.item_detail.view.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [DogListActivity]
 * in two-pane mode (on tablets) or a [DogDetailActivity]
 * on handsets.
 */
class DogDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: Dog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_DOG)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = it.getSerializable(ARG_DOG) as Dog?
                activity?.dog_details_toolbar?.title = item?.name

                val bitmap = item?.imageUri?.let { it1 ->
                    PictureTools.decodeSampledBitmapFromUri(
                        it1, 500, 500)
                }
                activity?.dog_details_image?.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.item_detail.text = "${it.name}'s further details of its being"
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_DOG = "item_id"
    }
}
