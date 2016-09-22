//package com.table20.remed.charting;
//
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//
//import java.util.ArrayList;
//
//
//public class RealTimeLineData {
//
//    private int channels = 0;
//    private long maxX = 0;
//    private long[] lastX;
//    private int samples = 1000;
//    private LineData lineData;
//    private boolean debug = false;
//
//    public RealTimeLineData(int samples) {
//        this.samples = samples;
//    }
//
//    public void setDebug() {
//        debug = true;
//    }
//
//    private void msg(String txt) {
//        if (debug) {
//            System.out.println(txt);
//        }
//    }
//
//    private static ArrayList<String> createXValues(int samples) {
//        ArrayList<String> xValues = new ArrayList<>();
//        for (int i = 0; i < samples; i++) {
//            xValues.add(String.valueOf((i + 1)));
//        }
//        return xValues;
//    }
//
//    public void setLineDataSets(ArrayList<ILineDataSet> lineDataSets) {
//        channels = lineDataSets.size();
//        lastX = new long[channels];
//        for (int i = 0; i < channels; i++) {
//            lastX[i] = -1;
//        }
//        lineData = new LineData(createXValues(samples), lineDataSets);
//    }
//
//    public LineData getLineData() {
//        return lineData;
//    }
//
//    public void notifyDataChanged() {
//        lineData.notifyDataChanged();
//    }
//
//    public boolean addSample(int channel, long x, float value) {
//        if (x < lastX[channel]) {
//            return false;
//        }
//        ILineDataSet dataset;
//
//        // Shift all datasets
//        if (x > maxX) {
//            int delta = (int) (x - maxX);
//            for (int ch = 0; ch < channels; ch++) {
//                dataset = lineData.getDataSetByIndex(ch);
//                shiftAndPruneDataset(dataset, delta);
//            }
//            maxX = x;
//        }
//
//        // Add sample
//        int index = (int) (maxX - x + samples - 1);
//        dataset = lineData.getDataSetByIndex(channel);
//        dataset.addEntry(new Entry(value, index));
//        lastX[channel] = x;
//
//        return true;
//    }
//
//    /**
//     * Shifts the entries in a dataset and pruned the entries that fall off the end of the dataset.
//     *
//     * @param dataset to be shifted and pruned
//     * @param delta   amount to shift by.
//     */
//    private void shiftAndPruneDataset(ILineDataSet dataset, int delta) {
//        Entry e;
//        int index;
//        int newIndex;
//        int n0 = dataset.getEntryCount() - 1;
//        for (int i = n0; i > -1; i--) {
//            e = dataset.getEntryForIndex(i);
//            index = e.getXIndex();
//            newIndex = index - delta;
//            if (newIndex < 0) {
//                dataset.removeEntry(e);
//            } else {
//                e.setXIndex(newIndex);
//            }
//        }
//    }
//
//}
