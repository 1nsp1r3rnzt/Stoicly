//package codehealthy.com.stoicly.data;
//
//import android.app.Application;
//import android.arch.lifecycle.LiveData;
//import android.os.AsyncTask;
//
//import java.util.List;
//
//import codehealthy.com.stoicly.data.model.QuoteGroup;
//import codehealthy.com.stoicly.data.source.local.QuoteGroupDao;
//
//public class QuoteGroupRepositoryImpl implements QuoteGroupRepository {
//    private LiveData<List<QuoteGroup>> quoteGroupList;
//    private QuoteGroupDao              quoteGroupDao;
//
//    public QuoteGroupRepositoryImpl(Application appContext) {
//        quoteGroupDao = AppDatabase.getInstance(appContext).quoteGroupDao();
//        quoteGroupList = quoteGroupDao.getAllQuoteGroup();
//
//    }
//
//    @Override
//    public LiveData<List<QuoteGroup>> getAllQuoteGroup() {
//        return quoteGroupList;
//    }
//
//    @Override
//    public List<QuoteGroup> getQuoteGroupByCategoryId(int categoryId ) {
//
//    }
//
//    @Override
//    public void updateQuoteGroup(QuoteGroup quoteGroup) {
//
//    }
//
//    @Override
//    public void createQuoteGroup(QuoteGroup quoteGroups) {
//
//    }
//
//    @Override
//    public void deleteQuoteGroup(QuoteGroup quoteGroup) {
//
//    }
//
//    private static class GetQuoteGroupByCategoryIdAsysncTask extends AsyncTask<Integer, Void, List<QuoteGroup>> {
//        private QuoteGroupDao quoteGroupDao;
//
//        GetQuoteGroupByCategoryIdAsysncTask(QuoteGroupDao quoteGroupDao) {
//            this.quoteGroupDao = quoteGroupDao;
//        }
//
//        @Override
//        protected List<QuoteGroup> doInBackground(Integer... integers) {
//            return quoteGroupDao.getQuoteGroupByCategoryId(integers[0]);
//        }
//
//
//    }
//
//    private static class UpdateQuoteGroupAsysncTask extends AsyncTask<QuoteGroup, Void, Void> {
//        private QuoteGroupDao quoteGroupDao;
//
//        public UpdateQuoteGroupAsysncTask(QuoteGroupDao quoteGroupDao) {
//            this.quoteGroupDao = quoteGroupDao;
//        }
//
//        @Override
//        protected Void doInBackground(QuoteGroup... quoteGroups) {
//            quoteGroupDao.updateQuoteGroup(quoteGroups[0]);
//            return null;
//        }
//    }
//
//    private static class createQuoteGroupAsysncTask extends AsyncTask<QuoteGroup, Void, Void> {
//        QuoteGroupDao quoteGroupDao;
//
//        createQuoteGroupAsysncTask(QuoteGroupDao quoteGroupDao) {
//            this.quoteGroupDao = quoteGroupDao;
//
//        }
//
//
//        @Override
//        protected Void doInBackground(QuoteGroup... quoteGroups) {
//            quoteGroupDao.createQuoteGroup(quoteGroups[0]);
//            return null;
//        }
//    }
//            private static class DeleteQuoteGroupAsysncTask extends AsyncTask<QuoteGroup, Void, Void> {
//                QuoteGroupDao quoteGroupDao;
//
//                DeleteQuoteGroupAsysncTask(QuoteGroupDao quoteGroupDao) {
//                    this.quoteGroupDao = quoteGroupDao;
//                }
//
//                @Override
//                protected Void doInBackground(QuoteGroup... quoteGroups) {
//                     quoteGroupDao.deleteQuoteGroup(quoteGroups[0]);
//                    return null;
//                }
//
//
//            }
//        }
