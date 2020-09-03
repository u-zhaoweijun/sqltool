package cn.tenmg.sqltool.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 文件工具类
 * 
 * @author 赵伟均
 * @see
 * 
 *      <p>
 *      https://www.cnblogs.com/jjh-java/p/6611081.html
 *      </p>
 *
 */
public abstract class FileUtils {

	private static final String JAR = "jar";

	/**
	 * 递归扫描指定包及其子包下指定后缀名的文件
	 * 
	 * @param basePackage 指定的包名
	 * @param suffix      指定的文件后缀名
	 * @return 扫描到的文件列表
	 * @throws IOException @see java.nio.file.spi.FileSystemProvider.newFileSystem
	 */
	public static List<String> scanPackage(String basePackage, String suffix) throws IOException {
		basePackage = basePackage.replaceAll("\\.", "/");
		URL url = ClassUtils.getDefaultClassLoader().getResource(basePackage);
		if (url != null) {
			FileSystemProvider provider = null;
			if (url.getProtocol().equals(JAR)) {
				provider = getZipFSProvider();
				if (provider != null) {
					FileSystem fs = provider.newFileSystem(
							Paths.get(url.getPath().replaceFirst("file:/", "").replaceFirst("!.*", "")),
							new HashMap<>());
					return walkFileTree(fs.getPath(basePackage), null, suffix);
				}
			} else if (url.getProtocol().equals("file")) {
				int end = url.getPath().lastIndexOf(basePackage);
				String basePath = url.getPath().substring(1, end);
				return walkFileTree(Paths.get(url.getPath().replaceFirst("/", "")), Paths.get(basePath), suffix);
			}
		}
		return null;
	}

	private static List<String> walkFileTree(Path path, Path basePath, String suffix) throws IOException {
		final List<String> result = new ArrayList<>();
		java.nio.file.Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			private String packageName = Objects.isNull(basePath) ? "" : basePath.toString();

			@Override
			public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException {
				if (arg0.toString().endsWith(suffix)) {
					result.add(arg0.toString().replace(packageName, "").substring(1).replace("\\", "/"));
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
				return FileVisitResult.CONTINUE;
			}

		});
		return result;
	}

	public static FileSystemProvider getZipFSProvider() {
		for (FileSystemProvider provider : FileSystemProvider.installedProviders()) {
			if (JAR.equals(provider.getScheme()))
				return provider;
		}
		return null;
	}

}
