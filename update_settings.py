import re

file_path = 'app/src/main/java/com/ameya/intelligence/ui/screens/settings/local/LocalSettingsScreen.kt'
with open(file_path, 'r') as f:
    content = f.read()

# Replace version snackbar name (Ameya -> Ameya)
content = content.replace('onSnackbar("Ameya Intelligence', 'onSnackbar("Ameya Intelligence')

# Replace update row behavior
update_row_pattern = r'IosSettingsRow\(\s*Icons\.Default\.SystemUpdate,[\s\S]*?onCheckForUpdate\(\)\s*\}'
new_update_row = '''IosSettingsRow(
            Icons.Default.SystemUpdate,
            UiStrings.Settings.CHECK_FOR_UPDATE,
            "Check Telegram for updates",
            false,
            true
        ) {
            context.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://t.me/+X9-RS8k8iGIwZDU1")))
        }'''
content = re.sub(update_row_pattern, new_update_row, content)

# Replace help row behavior
help_row_pattern = r'IosSettingsRow\(Icons\.AutoMirrored\.Filled\.Help, UiStrings\.Settings\.HELP_FEEDBACK, UiStrings\.Settings\.HELP_FEEDBACK_SUBTITLE, false, true\) \{[\s\S]*?\}'
new_help_row = '''IosSettingsRow(Icons.AutoMirrored.Filled.Help, UiStrings.Settings.HELP_FEEDBACK, UiStrings.Settings.HELP_FEEDBACK_SUBTITLE, false, true) {
            context.startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://t.me/+9hyEnNFoKlxmODll")))
        }'''
content = re.sub(help_row_pattern, new_help_row, content)

with open(file_path, 'w') as f:
    f.write(content)
print("Settings updated")
