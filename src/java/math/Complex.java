package math;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 复数扩展到了二维平面, 用于描述一维线性代数难以描述的问题. 在各个学科都有广泛的应用.
 * <p>
 * 参考资料
 * 1. http://science.jrank.org/pages/1655/Complex-Numbers-Arithmetic.html
 * 2. https://www.vitutor.com/arithmetic/complex_numbers/conjugate_complex.html
 * 3. https://introcs.cs.princeton.edu/java/32class/Complex.java.html
 * 4. http://blog.csdn.net/sunkun2013/article/details/12765527
 * 5. https://baike.baidu.com/item/%E5%A4%8D%E6%95%B0%E8%BF%90%E7%AE%97%E6%B3%95%E5%88%99
 */
@Getter
@Setter
public class Complex {
    private static final Logger log = LoggerFactory.getLogger(Complex.class);
    private double real;
    private double imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * 加法运算
     * (a+bi)+(c+di)=(a+c)+(b+d)i
     *
     * @param b
     * @return
     */
    public Complex add(Complex b) {
        double b_real = b.getReal();
        double b_imaginary = b.getImaginary();

        // 复数的加法就是两个复数的实部相加, 虚部相加
        double real = this.real + b_real;
        double imaginary = this.imaginary + b_imaginary;
        return new Complex(real, imaginary);
    }

    /**
     * 减法运算
     * (a+bi)-(c+di)=(a-c)+(b-d)i
     *
     * @param b
     * @return
     */
    public Complex subtract(Complex b) {
        double b_real = b.getReal();
        double b_imaginary = b.getImaginary();

        double real = this.real - b_real;
        double imaginary = this.imaginary - b_imaginary;
        return new Complex(real, imaginary);
    }

    /**
     * 乘法运算
     * (a+bi)(c+di)=(ac-bd)+(bc+ad)i
     *
     * @param b
     * @return
     */
    public Complex multiple(Complex b) {
        // 结果实部 = 实部相乘 - 虚部相乘
        double real = this.real * b.getReal() - this.imaginary * b.getImaginary();
        // 结果虚部 = 实部和虚部交叉相乘 然后把乘积加过相加
        double imaginary = this.imaginary * b.getReal() + this.real * b.getImaginary();
        return new Complex(real, imaginary);
    }

    /**
     * 除法运算
     * x=(ac+bd)/(c^2+d^2)
     * y=(bc-ad)/(c^2+d^2)
     *
     * @param b
     * @return
     */
    public Complex divides(Complex b) {
        double b_real = b.getReal();
        double b_imaginary = b.getImaginary();

        // 公共除数
        double common_divider = b_real * b_real + b_imaginary * b_imaginary;

        double real = (this.real * b_real + this.imaginary * b_imaginary) / common_divider;
        double imaginary = (this.imaginary * b_real - this.real * b_imaginary) / common_divider;
        return new Complex(real, imaginary);
    }

    /**
     * 拉伸和收缩: 同时把实部和虚部乘以N
     *
     * @return
     */
    public Complex scale(double n) {
        return new Complex(n * this.real, n * this.imaginary);
    }

    /**
     * 共轭
     * <p>
     * 共轭复数,两个实部相等,虚部互为相反数的复数互为共轭复数(Conjugate Complex Number).
     * 当虚部不为零时,共轭复数就是实部相等,虚部相反,如果虚部为零,其共轭复数就是自身.
     * (当虚部不等于0时也叫共轭虚数)复数z的共轭复数记作zˊ.同时, 复数zˊ称为复数z的复共轭(complex conjugate).
     * <p>
     * 共轭复数实际上用直角坐标系来看他们在X实数轴上是对称的
     * http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&fm=detail&lm=-1&st=-1&sf=2&fmq=1517131924539_R_D&fm=detail&pv=&ic=0&nc=1&z=&se=&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E5%85%B1%E8%BD%AD%E5%A4%8D%E6%95%B0
     *
     * @return
     */
    public Complex conjugate() {
        return new Complex(this.real, -this.imaginary);
    }

    /**
     * 什么时候复数等于0, 当且仅当一个复数的实部和虚部都为0时, 复数为0
     *
     * @return
     */
    public boolean isZero() {
        if (this.real == 0 && this.imaginary == 0) {
            return true;
        }
        return false;
    }

    /**
     * 倒数
     *
     * @return
     */
    public Complex reciprocal() {
        double scale = this.real * this.real + this.imaginary * this.imaginary;
        return new Complex(this.real / scale, -this.imaginary / scale);
    }

    /**
     * 指数
     *
     * @return
     */
    public Complex exponential() {
        return new Complex(
            Math.exp(this.real) * Math.cos(this.imaginary),
            Math.exp(this.real) * Math.sin(this.imaginary)
        );
    }

    /**
     * 正弦
     *
     * @return
     */
    public Complex sin() {
        return new Complex(
            Math.sin(this.real) * Math.cosh(this.imaginary),
            Math.cos(this.real) * Math.sinh(this.imaginary)
        );
    }

    /**
     * 余弦
     *
     * @return
     */
    public Complex cos() {
        return new Complex(
            Math.cos(this.real) * Math.cosh(this.imaginary),
            -Math.sin(this.real) * Math.sinh(this.imaginary)
        );
    }

    /**
     * 正切
     *
     * @return
     */
    public Complex tan() {
        return sin().divides(cos());
    }

    /**
     * 求模
     * |a|
     *
     * @return
     */
    public double abs() {
        return Math.hypot(real, imaginary);
    }

    /**
     * 判断两个复数是否相等
     *
     * @param x
     * @return
     */
    public boolean equals(Object x) {
        if (x == null) {
            return false;
        }
        if (this.getClass() != x.getClass()) {
            return false;
        }
        Complex that = (Complex) x;
        return (this.real == that.real) && (this.imaginary == that.imaginary);
    }

    public int hashCode() {
        return Objects.hash(this.real, this.imaginary);
    }

    public String toString() {
        if (this.imaginary == 0) return this.real + "";
        if (this.real == 0) return this.imaginary + "i";
        if (this.imaginary < 0) return this.real + "-" + (-this.imaginary) + "i";
        return this.real + "+" + this.imaginary + "i";
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        Complex a = new Complex(5.0, 6.0);
        Complex b = new Complex(-3.0, 4.0);
        log.info("a            = " + a);
        log.info("b            = " + b);
        log.info("real(a)      = " + a.getReal());
        log.info("imaginary(a) = " + a.getImaginary());
        log.info("b + a        = " + b.add(a));
        log.info("a - b        = " + a.subtract(b));
        log.info("a * b        = " + a.multiple(b));
        log.info("b * a        = " + b.multiple(a));
        log.info("a / b        = " + a.divides(b));
        log.info("(a / b) * b  = " + a.divides(b).multiple(b));
        log.info("conjugate(a) = " + a.conjugate());
        log.info("|a|          = " + a.abs());
        log.info("tan(a)       = " + a.tan());
    }
}
