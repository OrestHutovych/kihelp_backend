package org.example.kihelp_back.global.service;

import org.example.kihelp_back.transaction.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBotService extends TelegramLongPollingBot {
    @Value("${telegram.username}")
    private String botUsername;

    @Value("${telegram.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {}

    public void withdrawAdminMessage(Transaction transaction, Long cardNumber) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId("-1002140659403");
        String message = String.format("Користувач із Telegram %s: надіслав запит на зняття %sUAH. Номер картки: %s\n#withdraw",
                transaction.getUser().getTelegramId(), transaction.getAmount(), cardNumber);

        sendMessage.setText(message);
        sendMessage.setParseMode("Markdown");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Підтвердити переказ коштів");
        button.setCallbackData("confirm_withdraw");
        row.add(button);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        sendMessage.setReplyMarkup(keyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
