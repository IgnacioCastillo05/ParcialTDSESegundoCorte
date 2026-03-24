package mathServices;

import java.util.concurrent.atomic.AtomicLong;
import java.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class mathController {

    @GetMapping("/pellseq")
    public Array greeting(@RequestParam double value) {

        n0 = 0;
        n1 = 1;
        n = 0;
        for (int i = 0; i < n; i++) {
            n2 = 2 * n1 + n0;
            n0 = n1;
            n1 = n2;
        }
        return n;);
    }
}