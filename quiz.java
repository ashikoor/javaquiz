import java.util.*;

public class AdvancedQuiz {
    static class Question {
        String questionText;
        List<String> options;
        Set<Character> correctAnswers;
        String explanation;

        public Question(String questionText, List<String> options, Set<Character> correctAnswers, String explanation) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswers = correctAnswers;
            this.explanation = explanation;
        }
    }

    public static void main(String[] args) {
        List<Question> questions = Arrays.asList(
            new Question(
                "Which statements about Java are TRUE? (Multiple answers possible)",
                Arrays.asList(
                    "A. Java supports multiple inheritance through classes.",
                    "B. Java has automatic garbage collection.",
                    "C. Java code is always interpreted, never compiled.",
                    "D. Java uses a virtual machine for portability."
                ),
                new HashSet<>(Arrays.asList('B', 'D')),
                "Java supports garbage collection and uses a virtual machine for portability. It does not support multiple inheritance through classes (only interfaces), and Java code can be compiled to bytecode."
            ),
            new Question(
                "What will be the output of the following code?\n\nint[] arr = {1, 2, 3};\nSystem.out.println(arr[1]);",
                Arrays.asList(
                    "A. 1",
                    "B. 2",
                    "C. 3",
                    "D. Compilation Error"
                ),
                new HashSet<>(Collections.singletonList('B')),
                "Arrays in Java are zero-indexed, so arr[1] is 2."
            ),
            new Question(
                "Which interface provides random access to elements in Java Collections Framework?",
                Arrays.asList(
                    "A. List",
                    "B. Set",
                    "C. Queue",
                    "D. Map"
                ),
                new HashSet<>(Collections.singletonList('A')),
                "List provides random access using get(index)."
            ),
            new Question(
                "What is a valid way to create an immutable list in Java 9+?",
                Arrays.asList(
                    "A. List<String> list = List.of(\"A\", \"B\");",
                    "B. List<String> list = new ArrayList<>();",
                    "C. List<String> list = Arrays.asList(\"A\", \"B\");",
                    "D. List<String> list = Collections.unmodifiableList(new ArrayList<>());"
                ),
                new HashSet<>(Arrays.asList('A', 'D')),
                "List.of creates an immutable list (Java 9+). Collections.unmodifiableList is also immutable."
            )
        );
        Collections.shuffle(questions);

        Scanner scanner = new Scanner(System.in);
        int totalScore = 0;
        int maxScore = 0;
        List<String> mistakes = new ArrayList<>();

        for (Question q : questions) {
            System.out.println("\n" + q.questionText);
            q.options.forEach(System.out::println);

            System.out.print("Your answer (example: AB, or just D): ");
            String userInput = askWithTimeout(scanner, 15); // 15 seconds per question
            if (userInput == null) {
                System.out.println("Timeout! Moving to next question.");
                mistakes.add(q.questionText + "\nCorrect answers: " + q.correctAnswers + "\n" + q.explanation);
                maxScore += q.correctAnswers.size();
                continue;
            }
            Set<Character> userAnswers = new HashSet<>();
            for (char c : userInput.toUpperCase().replaceAll("[^A-D]", "").toCharArray()) userAnswers.add(c);

            int questionScore = 0;
            maxScore += q.correctAnswers.size();
            for (char c : q.correctAnswers) {
                if (userAnswers.contains(c)) questionScore++;
            }
            if (questionScore == q.correctAnswers.size() && userAnswers.size() == q.correctAnswers.size()) {
                System.out.println("Correct!");
            } else if (questionScore > 0) {
                System.out.println("Partially correct.");
                mistakes.add(q.questionText + "\nCorrect answers: " + q.correctAnswers + "\n" + q.explanation);
            } else {
                System.out.println("Incorrect.");
                mistakes.add(q.questionText + "\nCorrect answers: " + q.correctAnswers + "\n" + q.explanation);
            }
            totalScore += questionScore;
        }

        System.out.println("\nQuiz finished! Your score: " + totalScore + "/" + maxScore);
        if (!mistakes.isEmpty()) {
            System.out.println("\nReview your mistakes:");
            mistakes.forEach(m -> System.out.println("\n" + m));
        }
    }

    // Timed input: returns null if timeout
    public static String askWithTimeout(Scanner scanner, int timeoutSeconds) {
        final String[] result = {null};
        Thread inputThread = new Thread(() -> {
            if (scanner.hasNextLine()) result[0] = scanner.nextLine();
        });
        inputThread.start();
        try {
            inputThread.join(timeoutSeconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result[0];
    }
                             }
