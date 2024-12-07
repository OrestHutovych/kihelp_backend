package org.example.kihelp_back.argument.usecase;

import org.example.kihelp_back.argument.model.ArgumentResponse;

import java.util.List;

public interface ArgumentGetUseCase {
    List<ArgumentResponse> getArguments();
}
