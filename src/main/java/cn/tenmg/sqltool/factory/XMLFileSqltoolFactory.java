package cn.tenmg.sqltool.factory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import cn.tenmg.sqltool.SqltoolFactory;
import cn.tenmg.sqltool.config.ConfigLoader;
import cn.tenmg.sqltool.config.loader.XMLConfigLoader;
import cn.tenmg.sqltool.config.model.Dsql;
import cn.tenmg.sqltool.config.model.Sqltool;
import cn.tenmg.sqltool.exception.IllegalConfigException;
import cn.tenmg.sqltool.utils.ClassUtils;
import cn.tenmg.sqltool.utils.CollectionUtils;
import cn.tenmg.sqltool.utils.StringUtils;

/**
 * 基于XML文件配置的Sqltool工厂
 * 
 * @author 赵伟均
 *
 */
public class XMLFileSqltoolFactory extends AbstractSqltoolFactory implements SqltoolFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8125151681490092061L;

	private static final Logger log = Logger.getLogger(XMLFileSqltoolFactory.class);

	private static final ConfigLoader loader = XMLConfigLoader.getInstance();

	private String basePackages;

	private String suffix = ".dsql.xml";

	public String getBasePackages() {
		return basePackages;
	}

	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	private XMLFileSqltoolFactory() {
		super();
	}

	public static final XMLFileSqltoolFactory bind(String basePackages, boolean showSql) {
		XMLFileSqltoolFactory factor = new XMLFileSqltoolFactory();
		factor.setBasePackages(basePackages);
		factor.setShowSql(showSql);
		factor.init();
		return factor;
	}

	public static final XMLFileSqltoolFactory bind(String basePackages, String suffix, boolean showSql) {
		XMLFileSqltoolFactory factor = new XMLFileSqltoolFactory();
		factor.setBasePackages(basePackages);
		factor.setSuffix(suffix);
		factor.setShowSql(showSql);
		factor.init();
		return factor;
	}

	private void init() {
		if (StringUtils.isBlank(basePackages)) {
			log.warn("参数basePackages没有配置");
		} else {
			String[] basePackages = this.basePackages.split(",");
			for (int i = 0; i < basePackages.length; i++) {
				String basePackage = basePackages[i];
				try {
					if (log.isInfoEnabled()) {
						log.info("扫描包：".concat(basePackage));
					}
					List<Object> files = getSqlResources(basePackage.replaceAll("\\.", "/"));
					if (CollectionUtils.isEmpty(files)) {
						log.warn("包：".concat(basePackage).concat("没有找到后缀名为").concat(suffix).concat("的文件"));
					} else {
						for (int j = 0, size = files.size(); j < size; j++) {
							Object file = files.get(j);
							Sqltool sqltool;
							String fileName;
							if (file instanceof File) {
								File f = ((File) file);
								fileName = f.getName();
								if (log.isInfoEnabled()) {
									log.info("开始解析".concat(fileName));
								}
								sqltool = loader.load(f);
							} else {
								fileName = (String) file;
								if (log.isInfoEnabled()) {
									log.info("开始解析".concat(fileName));
								}
								ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
								InputStream is = classLoader.getResourceAsStream(fileName);
								sqltool = loader.load(is);
							}
							List<Dsql> dsqls = sqltool.getDsql();
							if (!CollectionUtils.isEmpty(dsqls)) {
								for (Iterator<Dsql> dit = dsqls.iterator(); dit.hasNext();) {
									Dsql dsql = dit.next();
									this.dsqls.put(dsql.getId(), dsql);
								}
							}
							if (log.isInfoEnabled()) {
								log.info("完成解析".concat(fileName));
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					String msg = "扫描包：".concat(basePackage).concat("加载SQL文件失败");
					throw new IllegalConfigException(msg, e);
				}
			}
		}
	}

	private List<Object> getSqlResources(String resourceDir) throws URISyntaxException, IOException {
		List<Object> result = new ArrayList<Object>();
		String realRes;
		Enumeration<URL> urls;
		URL url;
		if (StringUtils.isNotBlank(resourceDir)) {
			// 统一全角半角，用逗号分隔
			String[] dirSet = resourceDir.replaceAll("\\；", ",").replaceAll("\\;", ",").split("\\,");
			for (String dir : dirSet) {
				realRes = dir.trim();
				urls = getResourceUrls(realRes);
				if (urls != null) {
					while (urls.hasMoreElements()) {
						url = urls.nextElement();
						if (url.getProtocol().equals("jar")) {
							if (realRes.charAt(0) == '/') {
								realRes = realRes.substring(1);
							}
							JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
							Enumeration<JarEntry> entries = jar.entries();
							// 同样的进行循环迭代
							JarEntry entry;
							String name;
							while (entries.hasMoreElements()) {
								// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
								entry = entries.nextElement();
								name = entry.getName();
								if (name.startsWith(realRes) && name.toLowerCase().endsWith(suffix)
										&& !entry.isDirectory()) {
									result.add(name);
								}
							}
						} else {
							getPathFiles(new File(url.toURI()), result);
						}
					}
				}
			}
		}
		return result;
	}

	private Enumeration<URL> getResourceUrls(String resource) throws IOException {
		Enumeration<URL> urls = null;
		if (StringUtils.isBlank(resource)) {
			return urls;
		}
		File file = new File(resource);
		if (file.exists()) {
			Vector<URL> v = new Vector<URL>();
			v.add(file.toURI().toURL());
			urls = v.elements();
		} else {
			if (resource.charAt(0) == '/') {
				resource = resource.substring(1);
			}
			urls = Thread.currentThread().getContextClassLoader().getResources(resource);
		}
		return urls;
	}

	private void getPathFiles(File parentFile, List<Object> fileList) {
		if (null == parentFile)
			return;
		String fileName = parentFile.getName();
		if (parentFile.isDirectory()) {
			File[] files = parentFile.listFiles();
			File file;
			for (int loop = 0; loop < files.length; loop++) {
				file = files[loop];
				fileName = file.getName();
				if (file.isDirectory()) {
					getPathFiles(files[loop], fileList);
				} else {
					if (fileName.endsWith(suffix)) {
						fileList.add(file);
					}
				}
			}
		} else if (fileName.endsWith(suffix)) {
			fileList.add(parentFile);
		}
	}

}
