package com.example.demo;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SortController {

    @GetMapping("/") // Menampilkan form input pertama kali
    public String showForm(Model model) {
        return "index"; // Kembali ke halaman index.html
    }

    @PostMapping("/") // Menangani form POST
    public String sortArray(@RequestParam("array") String arrayInput,
            @RequestParam("method") String method,
            Model model) {
        // Log input untuk mempermudah debugging
        System.out.println("Input Array: " + arrayInput);
        System.out.println("Sorting Method: " + method);

        String[] stringNumbers = arrayInput.split(",");
        int[] numbers;
        try {
            numbers = Arrays.stream(stringNumbers)
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (NumberFormatException e) {
            model.addAttribute("result", "Input hanya boleh berisi angka!");
            return "index"; // Kembali ke halaman input dengan error
        }

        // Sort array berdasarkan metode yang dipilih
        if ("bubble".equals(method)) {
            bubbleSort(numbers);
        } else if ("quick".equals(method)) {
            quickSort(numbers, 0, numbers.length - 1);
        }

        // Log hasil sorting
        System.out.println("Sorted Array: " + Arrays.toString(numbers));

        // Kirim hasil ke view
        String result = "Hasil: " + Arrays.toString(numbers);
        model.addAttribute("result", result);

        return "index"; // Kembali ke halaman utama dengan hasil
    }

    // Implementasi Bubble Sort
    private void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Implementasi Quick Sort
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}
