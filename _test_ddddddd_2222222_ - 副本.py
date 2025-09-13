import os

def list_files_and_content_to_txt(root_dir, output_file="all_files_with_content.txt"):
    """
    递归遍历 root_dir 下所有文件夹，把所有文件路径和内容写入 output_file
    """
    with open(output_file, "w", encoding="utf-8") as out_f:
        for dirpath, dirnames, filenames in os.walk(root_dir):
            for file in filenames:
                full_path = os.path.join(dirpath, file)
                out_f.write(f"=== {full_path} ===\n")  # 文件路径作为标题

                try:
                    # 尝试以 utf-8 读取文本内容
                    with open(full_path, "r", encoding="utf-8") as f:
                        content = f.read()
                except Exception:
                    # 如果不是文本文件或者编码错误，则标注为二进制文件
                    content = "[Binary file or unreadable content]"

                out_f.write(content + "\n\n")  # 文件内容写入，文件间空行分隔

if __name__ == "__main__":
    root_directory = r"Y:\新建文件夹 (278)\ForgeDevEnv-master\src\main\java\com"+r"\_test_ddddddd_fjklsdjklfsdjkl_shit_shit_grd_lj_ddddddd_bg_y_ddddd_old_yg_lyg_x_d_dddd_"+r""#r".\src"#"/path/to/your/folder"  # 替换为你要遍历的文件夹路径
    output_txt = "all_files_with_content_222_.txt"  # 输出 txt 文件名
    list_files_and_content_to_txt(root_directory, output_txt)
    print(f"所有文件路径和内容已写入 {output_txt}")
