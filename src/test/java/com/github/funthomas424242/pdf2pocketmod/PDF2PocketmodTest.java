package com.github.funthomas424242.pdf2pocketmod;

/*-
 * #%L
 * pdf2pocketmod
 * %%
 * Copyright (C) 2018 - 2019 PIUG
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.lowagie.text.pdf.codec.Base64;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.security.krb5.Config;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


class PDF2PocketmodTest {

    final static String IMAGE1_BASE64 = "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\n" +
            "HBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\n" +
            "MjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCANLAlQDASIA\n" +
            "AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\n" +
            "AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\n" +
            "ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\n" +
            "p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\n" +
            "AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\n" +
            "BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\n" +
            "U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\n" +
            "uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+iii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKzdc1/SvDenHUNYvEtLUOE8x\n" +
            "wT8x6DABNXbW5hvbSG6tpFlgmRZI5F6MpGQR9QaAJaKKKACiiigAooooAKKKz7fW9NutZvNIgule\n" +
            "/s1R7iEKcxhhlTnGOR6UAaFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAB\n" +
            "RRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFF\n" +
            "FFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUVDd3UNjZz3dw4jggjaWRz0VVGSfyFAHmn\n" +
            "i6CPxt8U9I8JyL5mmaXbvqGoL2LsNsan3GQfoxq38IdQng0jUfCV++6/8PXTWuT1eEkmNvp1A9gK\n" +
            "5jwL4Q1DxlBqPjWXxDrGjz6zdyOkdjIEzCp2oGyCTjBA9gKdNpM/ww+KWi6tNq99qNjr2bC9ub1g\n" +
            "zrJ8vlkkAccL+CtQB3/hrxZc654x8VaLNbxRxaNLAkUiE7pPMVid2fTb2pln4wmm+ImueHZreJLT\n" +
            "TbKO6Ey5LtuAJBHTua5Xwxq+neHfi/49ttYvrewe8a1ntzcyCNZEVGyQzYB+8P19DUXhLW7TWPjT\n" +
            "4v1PTd13a/2bGImjHE+zap2E8EFgQD0OKALuneNPHniTRX8SaBomiNo5Mhhtbi4kN3MqEg42jaGO\n" +
            "Dwf1rb1f4gnSvBOnay+jXa6pqTpb2ulTApKZ2JGw5GQODzjkY9a89sdL8Dajps/iDw74su/Bl5uc\n" +
            "z2f20IInBOQ8JOT06A45wB2pLrV9b1PwF4H8ba3DJMNK1Pzbx0iwXg3bRNtA9FH5g9DQB0XiXx34\n" +
            "+8FaE+qa7oGjzQybVjexnkIgckYWUN1BGRlTjOPWun8R+KtYs7jSdL0HRhfanqSGTzJmZLa3UDJZ\n" +
            "2A+uAOePoDxXxj8e+G9S+G13p2l6ra6hc3xiKpbSBzGqyKxZ8fd6AYODlhVzxdrl+/jLw/4WbxA/\n" +
            "h7SbnTftMt7GVR5ZASPLEjcLgAH8fpQBqW/jDxXoni7SdE8XabpXk6szx213pcshVXUZ2sr89wO3\n" +
            "XvzXPHUdetfjn4stPDunW11fXNta5lu5CkECLGuWfbyckgADn8qxNVt9Bsfij4Mt9K1/UNbukv8A\n" +
            "/S7m7vjciPONibh8oY4bgc8Cuo07xFpeifHrxZb6ndRWgvLa0EU0zBU3LEp2ljwCQcjPXBoA6Lwz\n" +
            "4x1WbxRceFfFGn21nrEcH2mCW0kLQXMWcEru5BB7H0PTFZ9v4z8WeK7y+fwZpelf2VZzNB9t1SWQ\n" +
            "faXX73lqg4HueP5CpDfW3iv46Wd5o0qXVjoumyJdXcR3RmSQkBAw4J5zx6H0ri/h7oWhw6ZeaNr3\n" +
            "i3WNC1fTrmSOe0TVfssZXOQ6KeCCO4+vQigD0vTfiDNfeEvEl5cacLPXPD8Mxu7F33KHRGZSGHVG\n" +
            "2n8j9Ti2fjf4ga54Yi8R6P4c0lbAQeY0d1O/nTlR85jA4VchgNxycZ71kaNbaC/g74j6joT6zdRv\n" +
            "YT28mo6hOJVuykMnKHGTjPU9iK7jwB/ySHRv+wYP/QTQBteEfEcPi3wrp+uwRGFLuMsYyc7GDFWG\n" +
            "e+GU81tV5/8ABL/kkOhf9vH/AKUSV6BQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAB\n" +
            "RRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFF\n" +
            "FFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABUdxbw3dvJb3MMc0EilXjkUMrA9QQeCKkooA86t/E\n" +
            "njyzt47e1+FMcEEY2pHFrlsqqPQALgCor7W/GuqQrDqHwjtruJWDqlxrVrIoYdCAVPPJ5969KooA\n" +
            "8s1W68Ua40bat8GbG+aP7jXOr2shX2BK9ParNvrPjOzn8+2+ENrBN5Yi8yLWbVW2DouQvQdh0r0q\n" +
            "igDyW7TXr++N9efBDS57sncZpdUtGZj6klOfxqbV9d+Kd1DaR6X4BjsFimVpkfVLWZJ4gCDFggbc\n" +
            "8cg8Yr1SigDwfxNYeMPE/h+40bS/hZb6NFPNG93JFe26tMqtu2jhe4HPP0rqdT1HxdrVslvqnwet\n" +
            "L2FOUS41m1kCn1GV4r0+igDy2G78UW1tBbwfBfT44LeQTQxpq1oFjkHR1Gzhvcc0Xt54p1H7R9t+\n" +
            "DNjcG52+eZdXtWMu3hdxK847Z6V6lRQB5pp2s+NNItBaab8IreztwciK31q2Rc+uAvWquqTeJdbm\n" +
            "WbVfgtp97Kgwr3GrWrsB6ZK5x7V6rRQB5umu+No9POnp8JLdbIoY/sw1q2EewjBXbtxj2p8HiPx3\n" +
            "a2qWtv8ACiKK3RdixR65bKir6ABcAV6LRQByHhC/8QNMdOv/AAJD4c02KJnieHUIZU37h8gjjAxn\n" +
            "czZ9veuvoooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA\n" +
            "KKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAo\n" +
            "oooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACii\n" +
            "igAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKK\n" +
            "ACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA\n" +
            "KKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAo\n" +
            "oooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKyPEOoXu\n" +
            "n2UB09bdrme5jgT7Ru2DccZOOa16w/Ev3NK/7Cdv/wChUAV8eNf+enh//vib/GqVh4xa18Wv4Z8R\n" +
            "XNhFqUqRSWaWqSYmDb8jnOCNnt1rsa8M8Wf8nPeG/wDr3j/lLQB7nRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABVPU9KsdYszaahbrPAWDbGJHI5B4q5RQB4NL8ANceZ2Txq6KWJC+U/A9Pv1o\n" +
            "+FfgdqGgeMNO1668SremzfcUaBtzDBGNxY4617RRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFZ+savDotmtzLBcT75ViSK3Tc7MxwABkVoVh+JfuaV/2E\n" +
            "7f8A9CoAr/8ACXN/0LXiD/wEX/4qruj+IIdZuLq3WzvbSe1CNJHdxBGw+7aRgn+6a168O8VSyJ+0\n" +
            "14dRZGVGt49yg4B4l60Ae40UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAVma7pUurWUUVve\n" +
            "fZJ4p0njm8sSYZTn7pIzWnRQB4NL4i+OaTOqaJGyBiFb7PHyPX71UtB0L4h618WtE8R+J9EkiW3I\n" +
            "jkmVUVVQK2MgH1avoaigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKiuLmC0iMtxNHDGDgvIwUfmalrA8WQQ3NrpsM8SSxPqUAZJFDKw3dwetAF/wDt3R/+\n" +
            "grY/+BCf41Ztr60vQxtLqCcL94xSBsfXFUv+Ea0H/oCab/4Cp/hXmGoa1deHPj3p2g6QlvaabqcE\n" +
            "JuoYoEAcr5uD04/CgD2OiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACsXxNb3s9jayWFr9q\n" +
            "nt7yKfyfMCblU88nitqigDxKT9pDSYpXjfw/fbkYqf3ydRXJWHjO38dfH3w7q9taS2sahYfLlYE5\n" +
            "VZDnj619HnStOJybC1J/64r/AIU6PTbGGQSRWVujr0ZYlBH44oAs0UUUAFFFFABRRRQAUUUUAFFF\n" +
            "FABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUU\n" +
            "AFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQA\n" +
            "UUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABR\n" +
            "RRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFF\n" +
            "FABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUU\n" +
            "AFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQA\n" +
            "UUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABR\n" +
            "RRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFYXioztY2cEF3cWpuL6GF5bdtrhSecHBo\n" +
            "A3aK5v8A4RFv+hl8Qf8AgWv/AMTXLxeLG8LfFGLwdPNqGpJqUUTwTXM4YwH95u7DIO0flQB6ZRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXO+MLqCxsNPu7mQRQQ6hA8kjdFG7qa6KigDlP8A\n" +
            "hZvggHB8T6b/AN/hXk+q63pmv/tI+G7zSb2G8thEiGSFty7gJMj9RXdSfAnwLLK8j2N1udix/wBK\n" +
            "fqau6J8HvB/h/WbbVdPtLhLu2bfGzXDMAcEdD9aAO8ooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKyfEGo3mm2UDWEMEtzPcxwIs7lUG44ySAT+lAGtRXN+\n" +
            "Z41/59PD/wD4Ezf/ABuqmn+MjB4pfw34hk0+11N0je0jtpHcTBt+fvKMEbP1oA6+iiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACsPxL9zSv+wnb/wDoVblUtU0my1mz+yX8Rlh3hwFkZCGByCCp\n" +
            "BH50AXa8M8Wf8nPeG/8Ar3j/AJS1DL8EfGbTO0fjmRULEqvmzcDsPvVe8L/BbXdG8a6b4g1LxHFf\n" +
            "/Y33EOHZ2GCMAsfegD2uiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\n" +
            "KKACiiigAooqhq+r2+i2a3Nwk8itIsSpBEZHZmOAAo5NAF+iub/4TKD/AKAviD/wVy/4Ve0fxBa6\n" +
            "1PcwQwXlvNbBDJHd27QsA2dpAbqPlP5UAa1FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQ\n" +
            "AUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAB\n" +
            "RRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFY\n" +
            "fiX7mlf9hO3/APQq3Ky9e0u41WyijtLxLS4hnSeOV4fNUFTnBXcufzoA1K8R8UXVxF+0v4fhjnlS\n" +
            "KW3j8xFchX4l6jvUEvjD42JK6p4XgdVYgN9kPI9f9ZWbomlfEDXvi7ofiTxJ4fktltyI5JI49iKg\n" +
            "D4J+Y92oA+h6KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiimySRxJvkdUX1Y4FADqKr/AG+z/wCfuD/v4KkinhnBMUqSY67GBxQBJRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABXPeL7W3vbPTra6gingk1GAPFKgZWG7oQeDXQ1ieJ4b2SxtJbGye8mt7\n" +
            "yKcwI6IzKp5wXIH60AJ/whfhX/oWtG/8AIv/AImvOLrWJfCnx2sPDmiWtlZaZqkEJuYobdUyV83B\n" +
            "GMYNNk/aO8OxSPG+i6qGQlSP3fUf8CrjbTxlZ+Of2gPDmrWNtPbwqqw7J8bsgSHPBPHNAH0vRRRQ\n" +
            "AUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAB\n" +
            "RRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFF\n" +
            "FFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQBmHw3oTEk6LpxJ5JNqn+FPg0LR7WdJrfSrGG\n" +
            "VDlXjt0Vl+hArQooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigA\n" +
            "ooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACi\n" +
            "iigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKK\n" +
            "KACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoooo\n" +
            "AKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigA\n" +
            "ooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACi\n" +
            "iigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKK\n" +
            "KACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoooo\n" +
            "AKKKKACsPxS9z9hs4bW9ns3uL2GFpoNu8Kx5xuBH6VuVzvjG7t7Cw0+7u5kht4tRgaSRzhVG7qTQ\n" +
            "A3/hFbz/AKG/xB/31b//ABmubg8XN4Z+Jsfg69u9S1Q6jFFJbT3Bi/cn95uztVeDtHaug/4WP4L/\n" +
            "AOho0r/wJX/GvJtX1nTdc/aS8N3elX0F5biFEMsDhl3ASZGR35FAH0DRRRQAUUUUAFFFFABRRRQA\n" +
            "UUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABR\n" +
            "RRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFF\n" +
            "FABRRRQAUUUUAFFFFABRRRQB5dL8AfBU0rysNR3OxY4ue5/4DV7Qfgx4T8O65a6vYC++1Wr74/Mn\n" +
            "3LnBHIx716HRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFZWv6nd6ZZQvY20Nxcz3EcEaTzGJMscZLBWI/I1q1h+JfuaV/2E7f/wBCoAr/AGrxt/0BvD//\n" +
            "AINpv/kaoNO8YFfEkvh7Xxp1hqhSN7aK3u2m88NvzjciHI2eneurrwzxZ/yc94b/AOveP+UtAHud\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFUdV0iz1qzFpfJK0QdZB5U7xMrKcghkIYfgav\n" +
            "UUAeDS/CL4lNM5j8eyLGWJVTqFzwO1WvDHwd8V6b460zxFrXiG31H7I+WLzSySFcEAAsPf1r2+ig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKo6tq1rotm\n" +
            "Lq784oXWNVggeZ2ZjgAKgLH8BV6sPxL9zSv+wnb/APoVAFf/AITbTv8AoH+IP/BFef8Axqr+keIb\n" +
            "HW5bmK1W7jltghljurSW3YBs7TtkUEg7TyPStWvEvE95dQftLaBbxXM0cM1vF5saOQr4EuMjofxo\n" +
            "A9tooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKK\n" +
            "ACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA\n" +
            "KKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigArK1/S7nVbKGOzvIrS5huI545ZYDMuVOcF\n" +
            "Qy5/MVq0UAeDS/ED4yxzOi+Do3VWIDDTpuR6/frM0O28d+I/jDoXiLxF4buLNYCI3kS1eONVCvgn\n" +
            "cT3b1r6MooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigD//2Q==";


    static Configuration configuration;

    PDF2Pocketmod app;


    @BeforeAll
    static void  setUpTestsuite(){
        configuration = new Configuration();
    }

    @BeforeEach
    void initialisiereTestfall(){
        app= new PDF2Pocketmod();
        Paths.get(".","/generated/src/test/resources/").toFile().mkdirs();
    }

    @Test
    void testRun(){
        try {
            app.run();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testInputOutput() throws Exception {



        final byte[] image1 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage1Filename()));
        assertNotNull(image1);
        assertEquals(IMAGE1_BASE64, Base64.encodeBytes(image1));
        final byte[] image2 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage2Filename()));
        assertNotNull(image2);
        final byte[] image3 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage3Filename()));
        assertNotNull(image3);
        final byte[] image4 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage4Filename()));
        assertNotNull(image4);
        final byte[] image5 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage5Filename()));
        assertNotNull(image5);
        final byte[] image6 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage6Filename()));
        assertNotNull(image6);
        final byte[] image7 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage7Filename()));
        assertNotNull(image7);
        final byte[] image8 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage8Filename()));
        assertNotNull(image8);

    }

}
