package dev.alphos.api.main.grpc.services;

import com.google.inject.ImplementedBy;
import dev.alphos.api.internal.grpc.services.DefaultNlpUtilService;

import java.util.List;
import java.util.Map;

@ImplementedBy(DefaultNlpUtilService.class)
public interface NlpUtilService {

  Map<String, Integer> generateFrequencyMap(List<String> tokens);
}
