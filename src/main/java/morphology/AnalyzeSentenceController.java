package morphology;

import com.google.gson.Gson;
import server.BaseController;
import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.analysis.SentenceAnalysis;
import zemberek.morphology.analysis.SentenceWordAnalysis;
import zemberek.morphology.analysis.SingleAnalysis;
import zemberek.morphology.analysis.WordAnalysis;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.post;
import static utils.ParameterHelper.getBooleanParam;
import static utils.ParameterHelper.showInput;

public class AnalyzeSentenceController extends BaseController {

    public AnalyzeSentenceController(Gson jsonConverter, TurkishMorphology morphology) {
        super(jsonConverter);
        initializeController(jsonConverter, morphology);
    }

    public void initializeController(Gson jsonConverter, TurkishMorphology morphology) {

        post("/analyze_sentence", (req, res) -> {
            boolean disambiguate = getBooleanParam(req, "disambiguate");
            boolean deepWordAnalysis = getBooleanParam(req, "deep_word_analysis");
            String sentence = req.queryParams("sentence");
            SentenceResults sentence_result = new SentenceResults();
            if (showInput(req)) {
                sentence_result.input = sentence;
            }
            List<SentenceItem> sentence_item_list = new ArrayList<>();
            if (disambiguate) {
                if (deepWordAnalysis) {
                    SentenceAnalysis results = morphology.analyzeAndDisambiguate(sentence);
                    for (SentenceWordAnalysis wordAnalysis : results) {
                        List<AnalyzeWordItem> analyze_list = new ArrayList<>();
                        SentenceItem sentence_item = new SentenceItem();
                        sentence_item.input = wordAnalysis.getWordAnalysis().getInput();
                        for (SingleAnalysis singleAnalysis : wordAnalysis.getWordAnalysis()) {
                            analyze_list.add(AnalyzeWordItem.fromSingleAnalysis(singleAnalysis));
                        }
                        sentence_item.results = analyze_list;
                        sentence_item_list.add(sentence_item);
                    }
                } else {
                    List<SingleAnalysis> singleAnalyses = morphology.analyzeAndDisambiguate(sentence)
                            .bestAnalysis();
                    for (SingleAnalysis analysis : singleAnalyses) {
                        SentenceItem sentence_item = new SentenceItem();
                        sentence_item.input = analysis.surfaceForm();
                        sentence_item.results = new ArrayList<>();
                        sentence_item.results.add(AnalyzeWordItem.fromSingleAnalysis(analysis));
                        sentence_item_list.add(sentence_item);
                    }
                }

            } else {
                if (deepWordAnalysis) {
                    List<WordAnalysis> results = morphology.analyzeSentence(sentence);
                    for (WordAnalysis wordAnalysis : results) {
                        List<AnalyzeWordItem> analyze_list = new ArrayList<>();
                        SentenceItem sentence_item = new SentenceItem();
                        sentence_item.input = wordAnalysis.getInput();
                        for (SingleAnalysis singleAnalysis : wordAnalysis.getAnalysisResults()) {
                            analyze_list.add(AnalyzeWordItem.fromSingleAnalysis(singleAnalysis));
                        }
                        sentence_item.results = analyze_list;
                        sentence_item_list.add(sentence_item);
                    }
                } else {
                    List<WordAnalysis> results = morphology.analyzeSentence(sentence);
                    for (WordAnalysis wordAnalysis : results) {
                        List<AnalyzeWordItem> analyze_list = new ArrayList<>();
                        SentenceItem sentence_item = new SentenceItem();
                        sentence_item.input = wordAnalysis.getInput();
                        SingleAnalysis singleAnalysis = wordAnalysis.getAnalysisResults().get(0);
                        analyze_list.add(AnalyzeWordItem.fromSingleAnalysis(singleAnalysis));
                        sentence_item.results = analyze_list;
                        sentence_item_list.add(sentence_item);
                    }
                }

            }
            sentence_result.results = sentence_item_list;
            return jsonConverter.toJson(sentence_result);
        });
    }
}

class SentenceResults {
    String input;
    List<SentenceItem> results;
}

class SentenceItem {
    String input;
    List<AnalyzeWordItem> results;
}