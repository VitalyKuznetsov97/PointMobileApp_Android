package com.vitaly_kuznetsov.point.chat_preview.presenter_layer;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.Chat;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.Chatmade;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.Message;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.chat_preview.model_layer.http_requests.HistoryGetRequestController;
import com.vitaly_kuznetsov.point.chat_preview.model_layer.recycler_view.MessagePreviewController;
import com.vitaly_kuznetsov.point.chat_preview.model_layer.recycler_view.MessagePreviewViewHolderModel;
import com.vitaly_kuznetsov.point.chat_preview.view_layer.ChatPreviewFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ChatPreviewViewPresenter implements BasicModelActionsInterface,ChatPreviewViewPresenterInterface{

    private UserDataModel userDataModel;
    private ChatPreviewFragment fragment;
    private boolean empty;

    private ArrayList<Chat> chats;

    private class ChatComparator implements Comparator<Chat>{
        @Override
        public int compare(Chat chat1, Chat chat2) {
            Message message1 = chat1.getMessages().get(chat1.getMessages().size() - 1);
            Message message2 = chat2.getMessages().get(chat2.getMessages().size() - 1);
            return Integer.parseInt(message1.getDate()) - Integer.parseInt(message2.getDate());
        }
    }

    private void sortChats(ArrayList<Chat> chatArrayList){
        Chat[] chatsArray = new Chat[chatArrayList.size()];
        chatsArray = chatArrayList.toArray(chatsArray);

        Comparator<Chat> backWardComparator = Collections.reverseOrder(new ChatComparator());
        Arrays.sort(chatsArray, backWardComparator);

        chats = new ArrayList<>(Arrays.asList(chatsArray));
    }

    @Override
    public void attachView(Fragment fragment) {
        this.userDataModel = ModelHandler.getInstance(fragment.getContext());
        this.fragment = (ChatPreviewFragment) fragment;
        this.fragment.getHomeActivity().showLoading();
        if (mayStartHttpRequest()) HistoryGetRequestController.getInstance().start(this);
    }

    @Override
    public void detachView() {
        this.fragment = null;
    }

    @Override public UserDataModel getUserDataModel() {
        return userDataModel;
    }

    @Override
    public void onResponse(PostModel postModel) {
        if (postModel != null && postModel.getStatus()) {
            if (postModel.getPayload() != null && postModel.getPayload().getStatus()){

                ArrayList<Chat> chatArrayList = postModel.getPayload().getData().getChats();

                if (chatArrayList != null && chatArrayList.size() != 0){
                    sortChats(chatArrayList);
                    showChatPreviewFragment(false);
                }
                else showChatPreviewFragment(true);
            }
            else showChatPreviewFragment(true);
        }
        else showChatPreviewFragment(true);
    }

    @Override
    public void onFailure(String errorMessage) {
        showChatPreviewFragment(true);
    }

    private void showChatPreviewFragment(boolean empty){
        this.empty = empty;
        if (this.fragment.getHomeActivity().getCurrentFragment() != null){
            this.fragment.getHomeActivity().hideLoading();
        }
        else {
            if (empty) this.fragment.getHomeActivity().hideLoading();
            this.fragment.getHomeActivity().showFrag(fragment);
        }
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public void onFragmentCreated(RecyclerView recyclerView) {
        if (chats != null && chats.size() != 0) {
            chats.add(0, new Chat());
            ArrayList<MessagePreviewViewHolderModel> messagePreviewViewHolderModels = new ArrayList<>();
            for (int i = 0; i < chats.size(); i++){

                MessagePreviewViewHolderModel messagePreviewViewHolderModel;

                if (i != 0) {
                    Chat chat = chats.get(i);
                    Chatmade chatmade = chat.getChatmade();
                    Message message = chat.getMessages().get(chat.getMessages().size() - 1);

                    messagePreviewViewHolderModel =
                            new MessagePreviewViewHolderModel(message.getText(),
                                    Long.parseLong(message.getDate()),
                                    chatmade.getNick());
                }
                else messagePreviewViewHolderModel = new MessagePreviewViewHolderModel();

                if (i == chats.size() - 1) messagePreviewViewHolderModel.setLast(true);
                messagePreviewViewHolderModels.add(messagePreviewViewHolderModel);
            }

            MessagePreviewController controller = new MessagePreviewController(recyclerView, fragment.getContext(), this);
            controller.setAdapter(messagePreviewViewHolderModels);
            this.fragment.getHomeActivity().hideLoading();
        }
    }

    @Override
    public void onRefreshClicked() {
        fragment.getHomeActivity().showChatPreview();
    }

    @Override
    public void onChatClicked(int chatPosition) {
        Chat chat = chats.get(chatPosition);
        Gson gson = new Gson();
        String chatString = gson.toJson(chat);
        fragment.getHomeActivity().goToChatActivity(chatString, false);
    }

    //--------------------------Empty-----------------------------------
    @Override public boolean mayStartHttpRequest() { return true; }
}
