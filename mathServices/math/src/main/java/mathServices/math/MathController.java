package mathServices.math;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/math")
public class MathController {

    @GetMapping("/pi")
    public double pi(){
        return Math.PI;
    }

    @GetMapping("/pellseq")
    public ArrayList pellseq(@RequestParam int value) {

        int n0 = 0;
        int n1 = 1;
        ArrayList Arr = new ArrayList();
        for (int i = 0; i < value; i++) {
            int n2 = 2 * n1 + n0;
            n0 = n1;
            n1 = n2;
            Arr.add(n2);
        }
        return Arr;
    }
}
