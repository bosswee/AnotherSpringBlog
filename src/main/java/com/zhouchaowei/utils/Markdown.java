package com.zhouchaowei.utils;

import com.zhouchaowei.support.web.MarkdownService;
import com.zhouchaowei.support.web.PegDownMarkdownService;

/**
 * @author wee
 *         <p>
 *         A Markdown processing util class
 * @date 1/19/16.
 */
public class Markdown {

    private static final MarkdownService MARKDOWN_SERVICE = new PegDownMarkdownService();

    public static String markdownToHtml(String content) {
        return MARKDOWN_SERVICE.renderToHtml(content);
    }
}
