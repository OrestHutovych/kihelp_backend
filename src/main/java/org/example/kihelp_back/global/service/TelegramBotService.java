package org.example.kihelp_back.global.service;

import org.example.kihelp_back.global.exception.TelegramException;
import org.example.kihelp_back.support.dto.SupportDto;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
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
                💰 *Поповнення балансу від користувача:*
                • *Username:* `@%s`
                • *Telegram ID:* `%s`
                • *Сума поповнення:* `%s UAH`
            
                🔗 *Деталі транзакції:*
                • *Transaction ID:* `%s`
            
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

    public void withdrawAdminMessage(Transaction transaction, String cardNumber) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        String message = String.format("""
                💳 *Запит на зняття коштів від користувача:*
                • *Username:* `@%s`
                • *Telegram ID:* `%s`
                • *Сума:* `%s UAH`
            
                🔗 *Деталі транзакції:*
                • *Transaction ID:* `%s`
                • *Номер картки:* `%s`
            
                #withdraw
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

    public void supportMessageSentToAdmin(User user, SupportDto supportDto) {
        String chatId = "1176171881";
        String message = String.format(
                """
                🚀 *Нове повідомлення від користувача:*
                 • *Username:* `@%s`
                 • *Telegram ID:* `%s`
                
                📝 *Повідомлення:* `%s`
                
                #support
                """,
                user.getUsername(),
                user.getTelegramId(),
                supportDto.message()
        );

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setParseMode("Markdown");

        try {
            Message sentMessage = execute(sendMessage);
            int messageId = sentMessage.getMessageId();

            for (MultipartFile file : supportDto.files()) {
                processAndSendFile(chatId, messageId, file);
            }
        } catch (TelegramApiException e) {
            throw new TelegramException(String.format(TELEGRAM_ERROR, e.getMessage()));
        }
    }


    private void processAndSendFile(String chatId, int messageId, MultipartFile file) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("file", file.getOriginalFilename());
            file.transferTo(tempFile);

            InputFile inputFile = new InputFile(tempFile);

            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(inputFile);
            sendDocument.setReplyToMessageId(messageId);

            execute(sendDocument);
        } catch (IOException | TelegramApiException e) {
            throw new TelegramException(String.format(TELEGRAM_ERROR, e.getMessage()));
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
