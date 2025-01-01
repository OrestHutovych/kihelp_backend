package org.example.kihelp_back.global.service;

import org.example.kihelp_back.global.exception.TelegramException;
import org.example.kihelp_back.transaction.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static org.example.kihelp_back.global.util.ErrorMessage.TELEGRAM_ERROR;

@Service
public class TelegramBotService extends TelegramLongPollingBot {
    @Value("${telegram.username}")
    private String botUsername;

    @lombok.Getter
    @Value("${telegram.token}")
    private String botToken;

    @Value("${telegram.chatId}")
    private String chatId;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        throw new UnsupportedOperationException();
    }

    public void depositAdminMessage(Transaction transaction) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        String message = String.format("""
                Користувач (Username: `@%s`, Telegram ID: `%s`) поповнив банас на %sUAH.
        
                Transaction ID: `%s`
                #deposit
                """,
                transaction.getUser().getUsername(),
                transaction.getUser().getTelegramId(),
                transaction.getAmount(),
                transaction.getTransactionId()
        );

        sendMessage.setText(message);
        sendMessage.setParseMode("Markdown");

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new TelegramException(
                    String.format(
                            TELEGRAM_ERROR, e.getMessage()
                    )
            );
        }
    }

    public void withdrawAdminMessage(Transaction transaction, Long cardNumber) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        String message = String.format("""
                Користувач (Username: `@%s`, Telegram ID: `%s`) надіслав запит на зняття %sUAH.
                
                "Transaction ID: `%s`
                "Номер картки: `%s`
                "#withdraw
                """,
                transaction.getUser().getUsername(),
                transaction.getUser().getTelegramId(),
                transaction.getAmount(),
                transaction.getTransactionId(),
                cardNumber
        );

        sendMessage.setText(message);
        sendMessage.setParseMode("Markdown");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton confirmButton = new InlineKeyboardButton();
        confirmButton.setText("Прийняти");
        confirmButton.setCallbackData("confirm_withdraw");

        InlineKeyboardButton rejectButton = new InlineKeyboardButton();
        rejectButton.setText("Відхилити");
        rejectButton.setCallbackData("reject_withdraw");

        row.add(confirmButton);
        row.add(rejectButton);

        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        sendMessage.setReplyMarkup(keyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new TelegramException(
                    String.format(
                            TELEGRAM_ERROR, e.getMessage()
                    )
            );
        }
    }
}
