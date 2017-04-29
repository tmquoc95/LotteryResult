package quocs.lottery_result;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Frg_RegionList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Frg_RegionList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frg_RegionList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private LotteryData lotteryData;
    private OnFragmentInteractionListener mListener;

    public Frg_RegionList() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Frg_RegionList newInstance(LotteryData lotteryData) {
        Frg_RegionList fragment = new Frg_RegionList();
        fragment.lotteryData = lotteryData;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_region_list, container, false);

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(),
//                android.R.layout.simple_list_item_1, lotteryData.lstValues);

        M00N_ListAdapter arrayAdapter = new M00N_ListAdapter(view.getContext(), android.R.layout.simple_list_item_1, lotteryData.lstValues);

        ListView listViewRegion = (ListView) view.findViewById(R.id.listViewRegion);
        listViewRegion.setAdapter(arrayAdapter);

        listViewRegion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.setRegionSelected(lotteryData.lstChildren[position]);
            }
        });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void setRegionSelected(LotteryDataPerRegion lotteryDataPerRegion);
    }
}

class M00N_ListAdapter extends ArrayAdapter<String> {

    M00N_ListAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvItemText = (TextView) convertView.findViewById(R.id.tvItemText);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvItemText.setText(this.getItem(position));

        return convertView;
    }

    @Override
    public View getDropDownView (int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    class ViewHolder {
        TextView tvItemText;

    }

}
