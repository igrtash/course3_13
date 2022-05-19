/** Урок 11: Строки и регулярные выражения
 * Практическое задание 13 - строки и работа с регулярными выражениями
 * В методе main создать StringBuilder, в который необходимо
 * положить стихотворение при помощь append() и insert().
 * Создать произвольный шаблон для действия с текстом.
 * Примеры:
 * Есть номер +79991234567 – после программы вывод номера
 * +79*******67
 * Скрыть электронную почту.
 * Вывести все слова из текста в которых больше 5 символов.
 * И т.д.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {
    public static void main(String[] args) {
        // стихотворение
        StringBuilder sb = new StringBuilder("От всех российских матерей…\n");
        int offset = sb.length();
        sb.append("Собой закрыл чужих детей…\n\n");
        sb.insert(offset, "За то, что он во имя мира\n");
        sb.append("Отцы становятся мудрей…\n");
        sb.insert(sb.indexOf("Отцы"), "Уходят в вечность ветераны,\n");
        sb.append("России — Родины своей…");
        sb.insert(sb.indexOf("России"), "И встали внуки на защиту\n");
        sb.insert(0, "Спасибо деду за Победу\n");
        System.out.println(sb);

        // Обработка текста
        sb.delete(0,sb.length()); // очистка
        sb.append("Уважаемый клиент!\n");
        sb.append("Ваш заказ зарегистрирован с номером 1234567.\n");
        sb.append("Ваши данные по профилю:\n");
        sb.append("\temail - User123456789zZ@yandex.Ru\n");
        sb.append("\tмоб. - +79991234567\n");
        sb.append("Данные об исполнении заказе № 1234567 будут направляться по email и SMS.\n\n");
        sb.append("С уважением,\n");
        sb.append("  администрация сайта.\n");
        //System.out.println(sb);

        // обработка текста email
        Pattern p = Pattern.compile("([a-zA-Z0-9._%-]{2})([a-zA-Z0-9._%-]+)(@)([a-zA-Z0-9.-]+)(\\.)([a-zA-Z]{2,4})");
        Matcher m = p.matcher(sb);

        // 1-ой способ через переменную output, что для памяти не очень хорошо
        String output = "";
        if (m.find()) {
            output = m.replaceAll("$1***$3***$5$6");
        } else
            System.out.println("not found email");
        // 2-ой способ, более эффективно работает с памятью
        m.reset();
        while(m.find()) {
            //System.out.println("[" + m.group()+"]");
            //System.out.println("start = " + m.start() + " end = " + m.end());
            //System.out.println(sb.substring(m.start(), m.end()));
            sb.replace(m.start(), m.end(),
                m.group(1) +
                    "*".repeat(m.group(2).length()) +
                    m.group(3) +
                    "*".repeat(m.group(4).length()) +
                    m.group(5) +
                    m.group(6)
            );
        }
        // обработка номера телефона
        // 1-ой способ через переменную output, что для памяти не очень хорошо
        p = Pattern.compile("([+0-9]{3})([+0-9]{8})([+0-9]{1})");
        m = p.matcher(output);
        if (m.find())
            output = m.replaceAll("$1********$3");
        else
            System.out.println("not found phone number");
        // 2-ой способ, более эффективно работает с памятью
        m = p.matcher(sb);
        m.reset();
        while(m.find()) {
            sb.replace(m.start(), m.end(),
                m.group(1) +
                    "*".repeat(m.group(2).length()) +
                    m.group(3)
            );
        }

        // печать текста после обработки
        System.out.println("\n>>>>>>>>>>>>>>>> 1-й способ <<<<<<<<<<<<<<<<\n");
        System.out.println(output);
        System.out.println("\n>>>>>>>>>>>>>>>> 2-й способ <<<<<<<<<<<<<<<<\n");
        System.out.println(sb);

        // печать слов длинной более 5 символов
        p = Pattern.compile("([а-яА-ЯёЁa-zA-Z]{6,})");
        m = p.matcher(output);
        while(m.find()) {
            System.out.println("[" + m.group()+"]");
        }
        /* int pos = 0;
        while ((m.find(pos))) {
            System.out.println("[" + m.group()+"]");
            pos = (m.end() + 1);
        }*/
    }
}
