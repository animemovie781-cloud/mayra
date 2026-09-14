import os
import glob

def rename_content_and_files(root_dir):
    # Step 1: Rename contents of all files
    for dirpath, dirnames, filenames in os.walk(root_dir):
        # Exclude build directories
        dirnames[:] = [d for d in dirnames if d not in ['.git', 'build', '.gradle', 'node_modules', '.idea']]
        
        for filename in filenames:
            file_path = os.path.join(dirpath, filename)
            # Skip non-text files based on extension or try-except
            if file_path.endswith(('.png', '.jpg', '.jpeg', '.webp', '.jar', '.keystore', '.so')):
                continue
                
            try:
                with open(file_path, 'r', encoding='utf-8') as f:
                    content = f.read()
                    
                new_content = content.replace('Ameya', 'Ameya')
                new_content = new_content.replace('ameya', 'ameya')
                new_content = new_content.replace('AMEYA', 'AMEYA')
                
                if new_content != content:
                    with open(file_path, 'w', encoding='utf-8') as f:
                        f.write(new_content)
            except Exception as e:
                pass # Skip files that cannot be read as utf-8

    # Step 2: Rename directories (bottom-up to avoid path issues)
    for dirpath, dirnames, filenames in os.walk(root_dir, topdown=False):
        dirnames[:] = [d for d in dirnames if d not in ['.git', 'build', '.gradle', 'node_modules', '.idea']]
        for dirname in dirnames:
            if 'ameya' in dirname.lower():
                old_dir = os.path.join(dirpath, dirname)
                new_dirname = dirname.replace('ameya', 'ameya').replace('Ameya', 'Ameya')
                new_dir = os.path.join(dirpath, new_dirname)
                os.rename(old_dir, new_dir)
                
    # Step 3: Rename files
    for dirpath, dirnames, filenames in os.walk(root_dir):
        dirnames[:] = [d for d in dirnames if d not in ['.git', 'build', '.gradle', 'node_modules', '.idea']]
        for filename in filenames:
            if 'ameya' in filename.lower():
                old_file = os.path.join(dirpath, filename)
                new_filename = filename.replace('ameya', 'ameya').replace('Ameya', 'Ameya')
                new_file = os.path.join(dirpath, new_filename)
                os.rename(old_file, new_file)

rename_content_and_files('/app/applet')
print("Renaming completed.")
