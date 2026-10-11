# Contributing

## Branches

- `main` - protected. Always working code. Nobody pushes directly.
- `develop` - integration branch. All feature branches merge here first.
- `feature/<module>` - one branch per module, created from `develop`.

Examples: `feature/login`, `feature/registration`, `feature/rating`,
`feature/jenkins-pipeline`, `feature/dockerfile`, `feature/k8s-deployment`,
`feature/tests`.

## Workflow

1. Update your local copy: `git checkout develop && git pull`
2. Create your branch: `git checkout -b feature/<module>`
3. Make small commits with clear messages (see below).
4. Push: `git push -u origin feature/<module>`
5. Open a Pull Request into `develop` on GitHub.
6. At least one teammate reviews it. The Git/GitHub Engineer merges it.
7. When `develop` is stable, it is merged into `main` through a Pull Request.

## Commit messages

Start with a verb, keep it short and specific.

```
Add login endpoint
Fix duplicate email check in registration
Update README with run steps
```

Not: `changes`, `final`, `fix2`.

## Pull request checklist

- [ ] The code builds: `mvn verify` passes
- [ ] No unrelated files (no `target/`, no IDE files)
- [ ] The PR description says what changed and why
