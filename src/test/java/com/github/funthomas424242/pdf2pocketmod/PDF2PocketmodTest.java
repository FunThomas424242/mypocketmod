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
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAorO1vXtM8N6adQ1e7W1tFYIZGUkZPQ\n" +
            "cAmuY/4XB4B/6GOD/v1J/wDE0AdxRWD4e8Z+HvFclwmh6nHeNbhTKERl2g5x94D0NU9e+JHg/wAM\n" +
            "3hs9W1yCG5X70SI8rJ/vBAdv40AdVRWP4f8AFeheKbdp9E1OC8VPvhCQ6em5Thh+Iqpr/jzwx4Xv\n" +
            "0sda1aOzuZIhMsbI7EoSQDwD3U/lQB0dFcrpfxK8G6zeJaWPiGzkuJDtSNyYyx9BuAyfauqoAKK4\n" +
            "7Vfir4H0W+eyvvEEC3CHa6xRyTbT3BKKQD7V0Gja9pXiGxF7pF/BeW5OC8TZ2n0I6g+xoA0aKKzt\n" +
            "N17TNYur+2sLtZ5rCYwXShSPLcZ4ORz0PSgDRopk00dvBJPK22ONS7sewAyTVbStVsdb0yDUtNuF\n" +
            "uLOcExyqCAwBI789QaALlFFRG6txdi0M8f2kxmUQ7xvKAgFsdcZIGfegCWiiigAooooAKKKKACii\n" +
            "igAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKK\n" +
            "ACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAIri2gu4vKuY\n" +
            "I5o852SIGGfoa8p8WaXp8fxs8DQJYWqwyRXW+NYVCtiNsZGMGvW68v8AF/8AyXTwF/1yu/8A0W1A\n" +
            "HQ+Or6HwZ4B1vV9Mtbe2uY4AqPFEq4dmCKeBzgtmofht4Q03QPCVhcC3jl1K9gS4u7uQbpJHcbjl\n" +
            "jzgZx+vUmtXxv4fbxT4K1XRUYCW5hxEW6eYpDJn23KK5fwJ8RdH/ALAttG8QXsGj63psS2t1bX7i\n" +
            "EkoMBlLYByBnj+WDQBl/E3T7XwZrWheOtIiS0uVvktb9YQFW5hcHO4Dgn5SM+4PYVPrFtBd/tHaT\n" +
            "FcwRzR/2ATskQMM+ZL2NU/GOr2vxO8Q6P4S8OyC+sbW8S91S+h5ijRc4QN0JOT07498S+JNW0/Rf\n" +
            "2htKvNTvYLO2GglTLM4VcmSXAyaAOu8X+AdB8R+HLyzfS7SK48pjbzxQqjxSAZUggZxnGR3rzi68\n" +
            "c6rP+zxp1ylw41W+mGlfaC3zfeYbs+pRMZ65JNdh4q+LHhq00S5h0fU4tU1aeMw2ltZ5lZ5GGF6c\n" +
            "YBOf5c1hXfw21KP4D2WiQJnW7J11FYxyTNuZin1CsVHqQKAPRfDXhDRvCuiw6bp9lCqogEspQb5m\n" +
            "xyzHuT+nSuC1ywt/Afxc8Oalo8S21n4gd7K+tYhtjZ+NjhRwDlh+R9TXSeHfil4X1nSkmvNVtNMv\n" +
            "o123VnezCF4pB94YfGRnuP0PFcxJfx/E34paLLo4abQPDjvPNfbSElnONqIT1wVX9fbIB67Xl/wr\n" +
            "/wCRt+In/Ybf/wBCevUK8a8BeJ9C0Dxj4/j1fVrOxeXWXaNbiUIWAZ8kZoA9a1SYW+kXs5jSQRwO\n" +
            "5RxlWwpOD7GvO7b4hHTvhj4d1Ox0WzjvdXuBaWdjCRDbxyM7DJPZeM/U/jXTz+KtA1/RtWt9J1iy\n" +
            "vZo7KV3SCYOVXaRk47ZIrzjTrrw9F8CvClr4p02a50q7m8p7hDgWjGR8SEg5Hfke478gHZ2mv+Ot\n" +
            "M17TrPxFoWn3VlfyeV9q0Uyv9mbsZQw+778AetcvbyeKB+0HqPkW2lF/7OVWDyyAfZPOXDDj/W4x\n" +
            "x096zro/8IPrOgp4M8cXGsxX19HA2jS3KXQMTfeZSv3APXA69eDXRvqdjpH7Q102o3cNqt1occUD\n" +
            "TOFEjmUYUE9ScHj2oA0rrxl4j1vxLqOjeC9N06VNLbyry/1OR1i83vGqp8xIwcn1/AnR8I+MLzVt\n" +
            "V1Hw/runpYa9p4V5I4n3xTRt0kjPXHIyD0yPcDzHQNE0m38Z+KtI8Q+JdW0K+OoyXVusGom1iuYX\n" +
            "OVYZ4ZvXn+RrqPh9Z+G3+IOrXOh3mt6rNZ2otpdUu7sTwPuZW8tWxkkEfTg+1AHqtFFFABRRRQAU\n" +
            "UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRR\n" +
            "RQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQBn63d6jY6P\n" +
            "Pc6Tpf8Aal8m3y7P7QsHmZYA/O3AwCTz1xjvXDy6742nvYL2b4SW8l1ACIZ31q2Lx54O1iuRn2r0\n" +
            "iigDz/8A4S34h/8ARMP/ACv2/wDhWXqtx4n1xlfVfgxY3rqMK9xq9q7AegJXIr1SigDzXT9b8a6V\n" +
            "bC2074R29nAORFb61bRr+QXFQahe+KdWnWfUfgxYXkyrsElxq1pIwXJOMshOMk8e9eo0UAeXWN74\n" +
            "p0ubztP+DGn2kvTfb6taRn8wgrR/4S34h/8ARMP/ACv2/wDhXoFFAHk+pf8ACQ6xc/adS+Cem3c/\n" +
            "/PWbVbR2P1JXJrStfEXjqxtktrT4URW8CDCRRa5bIq/QBcCvRqKAPP8A/hLfiH/0TD/yv2/+FY85\n" +
            "8QXVxJcXHwQ0qaaRi7ySalZszMepJKZJr1iigDy2yu/FGmtI1j8F9PtWlQxyGDVbRN6nqpwnI9qn\n" +
            "XWfGa6b/AGavwhtRYbSv2Uaza+VgnONm3GM+1el0UAeU6ZJ4k0W4a40v4K6fZTMMGS31a0RsemQu\n" +
            "ce1T3+o+LdUngn1D4OWV3NAcwyXGr2sjRnOflJXjn0r0+igDy7Vb3xXrqourfBuyvgn3PtOsWshX\n" +
            "6ZXitDQtX8XWc1np0fwug0rTWmVZHg1e32wISAz7FUZwOcDk4r0GigAooooAKKKKACiiigAooooA\n" +
            "KKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAo\n" +
            "oooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACii\n" +
            "igAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKK\n" +
            "ACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA\n" +
            "KKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAo\n" +
            "oooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACii\n" +
            "igAooooAKKKKACiiigAooooAKKKKACiiigArI8Q6he6fZQHT1t2uZ7mOBPtG7YNxxk45rXrD8S/c\n" +
            "0r/sJ2//AKFQBXx41/56eH/++Jv8apWHjFrXxa/hnxFc2EWpSpFJZpapJiYNvyOc4I2e3Wuxrwzx\n" +
            "Z/yc94b/AOveP+UtAHudFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFU9T0qx1izNpqFus8\n" +
            "BYNsYkcjkHirlFAHg0vwA1x5nZPGropYkL5T8D0+/Wj4V+B2oaB4w07XrrxKt6bN9xRoG3MMEY3F\n" +
            "jjrXtFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAVn\n" +
            "6xq8Oi2a3MsFxPvlWJIrdNzszHAAGRWhWH4l+5pX/YTt/wD0KgCv/wAJc3/QteIP/ARf/iqu6P4g\n" +
            "h1m4urdbO9tJ7UI0kd3EEbD7tpGCf7prXrw7xVLIn7TXh1FkZUa3j3KDgHiXrQB7jRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABWZrulS6tZRRW959kninSeObyxJhlOfukjNadFAHg0viL45p\n" +
            "M6pokbIGIVvs8fI9fvVS0HQviHrXxa0TxH4n0SSJbciOSZVRVVArYyAfVq+hqKACiiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAqK4uYLSIy3E0cMYOC8jBR+Z\n" +
            "qWsDxZBDc2umwzxJLE+pQBkkUMrDd3B60AX/AO3dH/6Ctj/4EJ/jVm2vrS9DG0uoJwv3jFIGx9cV\n" +
            "S/4RrQf+gJpv/gKn+FeYahrV14c+PenaDpCW9ppupwQm6higQByvm4PTj8KAPY6KKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKxfE1vez2NrJYWv2qe3vIp/J8wJuVTzyeK2qKAPEpP2kNJileN\n" +
            "/D99uRip/fJ1FclYeM7fx18ffDur21pLaxqFh8uVgTlVkOePrX0edK04nJsLUn/riv8AhTo9NsYZ\n" +
            "BJFZW6OvRliUEfjigCzRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUU\n" +
            "AFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQA\n" +
            "UUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABR\n" +
            "RRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFF\n" +
            "FABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUU\n" +
            "AFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQA\n" +
            "UUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABR\n" +
            "RRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFF\n" +
            "FABRRRQAUUVheKjO1jZwQXdxam4voYXlt22uFJ5wcGgDdorm/wDhEW/6GXxB/wCBa/8AxNcvF4sb\n" +
            "wt8UYvB082oakmpRRPBNczhjAf3m7sMg7R+VAHplFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFc74wuoLGw0+7uZBFBDqEDySN0UbuproqKAOU/wCFm+CAcHxPpv8A3+FeT6rrema/+0j4bvNJ\n" +
            "vYby2ESIZIW3LuAkyP1Fd1J8CfAssryPY3W52LH/AEp+pq7onwe8H+H9ZttV0+0uEu7Zt8bNcMwB\n" +
            "wR0P1oA7yiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "rJ8QajeabZQNYQwS3M9zHAizuVQbjjJIBP6UAa1Fc35njX/n08P/APgTN/8AG6qaf4yMHil/DfiG\n" +
            "TT7XU3SN7SO2kdxMG35+8owRs/WgDr6KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKw/Ev3\n" +
            "NK/7Cdv/AOhVuVS1TSbLWbP7JfxGWHeHAWRkIYHIIKkEfnQBdrwzxZ/yc94b/wCveP8AlLUMvwR8\n" +
            "ZtM7R+OZFQsSq+bNwOw+9V7wv8Ftd0bxrpviDUvEcV/9jfcQ4dnYYIwCx96APa6KKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiqGr6vb6LZrc3CTyK0ixKkE\n" +
            "RkdmY4ACjk0AX6K5v/hMoP8AoC+IP/BXL/hV7R/EFrrU9zBDBeW81sEMkd3btCwDZ2kBuo+U/lQB\n" +
            "rUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAB\n" +
            "RRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFF\n" +
            "FFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAVh+JfuaV/2E7f8A9CrcrL17S7jVbKKO0vEt\n" +
            "LiGdJ45Xh81QVOcFdy5/OgDUrxHxRdXEX7S/h+GOeVIpbePzEVyFfiXqO9QS+MPjYkrqnheB1ViA\n" +
            "32Q8j1/1lZuiaV8QNe+Luh+JPEnh+S2W3Ijkkjj2IqAPgn5j3agD6HooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK\n" +
            "KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoo\n" +
            "ooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiii\n" +
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKbJJHEm+R1RfVjgUAOoqv8Ab7P/AJ+4\n" +
            "P+/gqSKeGcExSpJjrsYHFAElFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFc94vtbe9s9Ot\n" +
            "rqCKeCTUYA8UqBlYbuhB4NdDWJ4nhvZLG0lsbJ7ya3vIpzAjojMqnnBcgfrQAn/CF+Ff+ha0b/wA\n" +
            "i/8Aia84utYl8KfHaw8OaJa2VlpmqQQm5iht1TJXzcEYxg02T9o7w7FI8b6LqoZCVI/d9R/wKuNt\n" +
            "PGVn45/aA8OatY209vCqrDsnxuyBIc8E8c0AfS9FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAB\n" +
            "RRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFF\n" +
            "FFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUU\n" +
            "UAFFFFAGYfDehMSTounEnkk2qf4U+DQtHtZ0mt9KsYZUOVeO3RWX6ECtCigAooooAKKKKACiiigA\n" +
            "ooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACi\n" +
            "iigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKK\n" +
            "KACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoooo\n" +
            "AKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigA\n" +
            "ooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACi\n" +
            "iigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKK\n" +
            "KACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoooo\n" +
            "AKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKw/FL3P2Gzhtb2eze4vYYWmg27w\n" +
            "rHnG4EfpW5XO+Mbu3sLDT7u7mSG3i1GBpJHOFUbupNADf+EVvP8Aob/EH/fVv/8AGa5uDxc3hn4m\n" +
            "x+Dr271LVDqMUUltPcGL9yf3m7O1V4O0dq6D/hY/gv8A6GjSv/Alf8a8m1fWdN1z9pLw3d6VfQXl\n" +
            "uIUQywOGXcBJkZHfkUAfQNFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABR\n" +
            "RRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFF\n" +
            "FABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAHl0vwB8FTS\n" +
            "vKw1Hc7Fji57n/gNXtB+DHhPw7rlrq9gL77Vavvj8yfcucEcjHvXodFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAF\n" +
            "FFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAVla/qd3pllC9jbQ3FzPcRwRpPMYkyx\n" +
            "xksFYj8jWrWH4l+5pX/YTt//AEKgCv8AavG3/QG8P/8Ag2m/+Rqg07xgV8SS+HtfGnWGqFI3tore\n" +
            "7abzw2/ONyIcjZ6d66uvDPFn/Jz3hv8A694/5S0Ae50UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUU\n" +
            "UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRR\n" +
            "QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFA\n" +
            "BRRRQAVR1XSLPWrMWl8krRB1kHlTvEyspyCGQhh+Bq9RQB4NL8IviU0zmPx7IsZYlVOoXPA7Va8M\n" +
            "fB3xXpvjrTPEWteIbfUfsj5YvNLJIVwQACw9/Wvb6KACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAC\n" +
            "iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKK\n" +
            "KKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooo\n" +
            "oAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiig\n" +
            "AooooAKKKKACiiigAooooAKKKKACiiigAqjq2rWui2YurvzihdY1WCB5nZmOAAqAsfwFXqw/Ev3N\n" +
            "K/7Cdv8A+hUAV/8AhNtO/wCgf4g/8EV5/wDGqv6R4hsdbluYrVbuOW2CGWO6tJbdgGztO2RQSDtP\n" +
            "I9K1a8S8T3l1B+0toFvFczRwzW8Xmxo5CvgS4yOh/GgD22iiigAooooAKKKKACiiigAooooAKKKK\n" +
            "ACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA\n" +
            "KKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAo\n" +
            "oooAKKKKACsrX9LudVsoY7O8itLmG4jnjllgMy5U5wVDLn8xWrRQB4NL8QPjLHM6L4OjdVYgMNOm\n" +
            "5Hr9+szQ7bx34j+MOheIvEXhu4s1gIjeRLV441UK+CdxPdvWvoyigAooooAKKKKACiiigAooooAK\n" +
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
            "gAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKA\n" +
            "CiiigAooooAKKKKAP//Z";


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



        final byte[] image1 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage1Filename()),0, Configuration.Orientation.AUTO);
        assertNotNull(image1);
        assertEquals(IMAGE1_BASE64, Base64.encodeBytes(image1));
        final byte[] image2 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage2Filename()),0 ,Configuration.Orientation.AUTO);
        assertNotNull(image2);
        final byte[] image3 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage3Filename()),0 ,Configuration.Orientation.AUTO);
        assertNotNull(image3);
        final byte[] image4 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage4Filename()),0 ,Configuration.Orientation.AUTO);
        assertNotNull(image4);
        final byte[] image5 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage5Filename()),0 ,Configuration.Orientation.AUTO);
        assertNotNull(image5);
        final byte[] image6 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage6Filename()),0 ,Configuration.Orientation.AUTO);
        assertNotNull(image6);
        final byte[] image7 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage7Filename()),0 ,Configuration.Orientation.AUTO);
        assertNotNull(image7);
        final byte[] image8 = app.getPDFPageAsBytes(Paths.get(configuration.getPocketmodPage8Filename()),0 ,Configuration.Orientation.AUTO);
        assertNotNull(image8);

    }

}
